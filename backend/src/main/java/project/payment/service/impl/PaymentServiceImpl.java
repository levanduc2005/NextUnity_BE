package project.payment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.payment.dto.request.CreatePaymentRequest;
import project.payment.dto.response.CreatePaymentResponse;
import project.payment.dto.response.PaymentResponse;
import project.payment.dto.response.SubscriptionPlanResponse;
import project.payment.entity.Payment;
import project.payment.entity.enums.PaymentStatus;
import project.payment.entity.enums.SubscriptionPlan;
import project.payment.mapper.PaymentMapper;
import project.payment.repository.PaymentRepository;
import project.payment.service.PaymentService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.entity.User;
import project.user.entity.enums.SubscriptionTier;
import project.user.repository.UserRepository;
import project.user.service.UserService;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.v2.paymentRequests.PaymentLink;
import vn.payos.model.v2.paymentRequests.PaymentLinkItem;
import vn.payos.model.v2.paymentRequests.PaymentLinkStatus;
import vn.payos.model.webhooks.Webhook;
import vn.payos.model.webhooks.WebhookData;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PayOS payOS;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PaymentMapper paymentMapper;

    @Value("${payos.return-url}")
    private String returnUrl;

    @Value("${payos.cancel-url}")
    private String cancelUrl;

    @Override
    @Transactional
    public CreatePaymentResponse createPayment(CreatePaymentRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        SubscriptionPlan plan = request.plan();

        // 1. Kiểm tra nếu người dùng đã có giao dịch PENDING trước đó
        var pendingOpt = paymentRepository.findFirstByUserIdAndStatusOrderByCreatedAtDesc(currentUserId, PaymentStatus.PENDING);
        if (pendingOpt.isPresent()) {
            Payment pending = pendingOpt.get();
            try {
                PaymentLink linkInfo = payOS.paymentRequests().get(pending.getOrderCode());
                if (linkInfo.getStatus() == PaymentLinkStatus.PAID) {
                    activateUserSubscription(currentUser, pending.getPlan());
                    pending.setStatus(PaymentStatus.PAID);
                    paymentRepository.save(pending);
                    throw new AppException(ErrorCode.PAYMENT_ALREADY_PAID);
                } else if (linkInfo.getStatus() == PaymentLinkStatus.PENDING) {
                    if (pending.getPlan() == plan) {
                        return paymentMapper.toCreateResponse(pending);
                    } else {
                        pending.setStatus(PaymentStatus.CANCELLED);
                        paymentRepository.save(pending);
                    }
                } else {
                    pending.setStatus(PaymentStatus.CANCELLED);
                    paymentRepository.save(pending);
                }
            } catch (AppException e) {
                throw e;
            } catch (Exception e) {
                log.warn("Không thể kiểm tra đơn pending cũ (mã {}), tiến hành tạo đơn mới: {}", pending.getOrderCode(), e.getMessage());
            }
        }

        // 2. Tạo mã orderCode độc nhất (System.currentTimeMillis())
        long orderCode = System.currentTimeMillis();
        String description = "NextUnity " + (plan == SubscriptionPlan.PLUS_MONTHLY ? "1 Thang" : "1 Ky");

        // 3. Gọi SDK PayOS để tạo link thanh toán
        try {
            PaymentLinkItem item = PaymentLinkItem.builder()
                    .name(plan.getName())
                    .price(plan.getPrice())
                    .quantity(1)
                    .build();

            CreatePaymentLinkRequest payOSRequest = CreatePaymentLinkRequest.builder()
                    .orderCode(orderCode)
                    .amount(plan.getPrice())
                    .description(description)
                    .returnUrl(returnUrl)
                    .cancelUrl(cancelUrl)
                    .item(item)
                    .build();

            CreatePaymentLinkResponse payOSResponse = payOS.paymentRequests().create(payOSRequest);

            // 4. Lưu đơn thanh toán vào cơ sở dữ liệu
            Payment payment = new Payment();
            payment.setOrderCode(orderCode);
            payment.setAmount(plan.getPrice());
            payment.setCheckoutUrl(payOSResponse.getCheckoutUrl());
            payment.setQrCode(payOSResponse.getQrCode());
            payment.setStatus(PaymentStatus.PENDING);
            payment.setPlan(plan);
            payment.setUser(currentUser);

            paymentRepository.save(payment);

            return paymentMapper.toCreateResponse(payment);
        } catch (Exception e) {
            log.error("Lỗi khi gọi PayOS SDK tạo thanh toán: ", e);
            throw new AppException(ErrorCode.PAYMENT_FAILED);
        }
    }

    @Override
    @Transactional
    public void handleWebhook(Webhook webhook) {
        WebhookData data;
        try {
            data = payOS.webhooks().verify(webhook);
        } catch (Exception e) {
            log.error("Xác thực chữ ký webhook PayOS thất bại: ", e);
            throw new AppException(ErrorCode.INVALID_WEBHOOK_SIGNATURE);
        }

        if (!Boolean.TRUE.equals(webhook.getSuccess()) || !"00".equals(webhook.getCode())) {
            log.warn("Webhook PayOS nhận trạng thái không thành công: code={}", webhook.getCode());
            return;
        }

        Long orderCode = data.getOrderCode();
        Payment payment = paymentRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));

        if (payment.getStatus() == PaymentStatus.PAID) {
            log.info("Đơn hàng #{} đã được xử lý trước đó, bỏ qua", orderCode);
            return;
        }

        payment.setStatus(PaymentStatus.PAID);
        activateUserSubscription(payment.getUser(), payment.getPlan());
        paymentRepository.save(payment);
        log.info("Kích hoạt thành công gói {} cho user {} từ Webhook", payment.getPlan(), payment.getUser().getEmail());
    }

    @Override
    @Transactional
    public PaymentResponse verifyPaymentStatus(Long orderCode) {
        Payment payment = paymentRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));

        if (payment.getStatus() != PaymentStatus.PAID) {
            try {
                PaymentLink linkInfo = payOS.paymentRequests().get(orderCode);
                if (linkInfo.getStatus() == PaymentLinkStatus.PAID) {
                    payment.setStatus(PaymentStatus.PAID);
                    activateUserSubscription(payment.getUser(), payment.getPlan());
                    paymentRepository.save(payment);
                } else if (linkInfo.getStatus() == PaymentLinkStatus.CANCELLED) {
                    payment.setStatus(PaymentStatus.CANCELLED);
                    paymentRepository.save(payment);
                }
            } catch (Exception e) {
                log.error("Lỗi khi kiểm tra trạng thái đơn hàng PayOS #{}: {}", orderCode, e.getMessage());
            }
        }

        return paymentMapper.toResponse(payment);
    }

    @Override
    public List<SubscriptionPlanResponse> getAvailablePlans() {
        return Arrays.stream(SubscriptionPlan.values())
                .map(paymentMapper::toPlanResponse)
                .toList();
    }

    @Override
    public Page<PaymentResponse> getMyPaymentHistory(Pageable pageable) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        return paymentRepository.findByUserIdOrderByCreatedAtDesc(currentUserId, pageable)
                .map(paymentMapper::toResponse);
    }

    private void activateUserSubscription(User user, SubscriptionPlan plan) {
        user.setSubscriptionTier(SubscriptionTier.PLUS);
        Instant baseTime = user.isPlus() ? user.getPremiumUntil() : Instant.now();
        user.setPremiumUntil(baseTime.plus(plan.getDurationDays(), ChronoUnit.DAYS));
        userRepository.save(user);
    }
}
