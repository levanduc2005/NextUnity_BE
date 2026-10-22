package project.payment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import project.payment.dto.response.PaymentResponse;
import project.payment.entity.enums.PaymentStatus;
import project.payment.mapper.PaymentMapper;
import project.payment.repository.PaymentRepository;
import project.payment.service.PaymentNotificationService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentNotificationServiceImpl implements PaymentNotificationService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    private final Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @Value("${payment.expire-minutes}")
    private long expireMinutes;

    @Override
    public SseEmitter subscribe(Long orderCode) {
        long timeoutMillis = expireMinutes * 60 * 1000L;
        SseEmitter emitter = new SseEmitter(timeoutMillis);

        emitter.onCompletion(() -> sseEmitters.remove(orderCode));
        emitter.onTimeout(() -> {
            sseEmitters.remove(orderCode);
            emitter.complete();
        });
        emitter.onError((e) -> sseEmitters.remove(orderCode));

        sseEmitters.put(orderCode, emitter);

        try {
            emitter.send(SseEmitter.event().name("INIT").data("Subscribed to order code: " + orderCode));
            paymentRepository.findByOrderCode(orderCode).ifPresent(payment -> {
                if(payment.getStatus() == PaymentStatus.PAID){
                    notifyPaymentSuccess(orderCode, paymentMapper.toResponse(payment));
                }
            });
        } catch (Exception e) {
            sseEmitters.remove(orderCode);
        }
        return emitter;
    }

    @Override
    public void notifyPaymentSuccess(Long orderCode, PaymentResponse response) {
        SseEmitter emitter = sseEmitters.remove(orderCode);

        if(emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("PAYMENT_SUCCESS").data(response));
                emitter.complete();
            } catch (Exception e) {
                log.error("Lỗi khi gửi SSE: ", e);
            }
        }
    }
}
