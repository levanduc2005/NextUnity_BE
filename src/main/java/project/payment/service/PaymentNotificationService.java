package project.payment.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import project.payment.dto.response.PaymentResponse;

public interface PaymentNotificationService {
    SseEmitter subscribe(Long orderCode);

    void notifyPaymentSuccess(Long orderCode, PaymentResponse response);
}
