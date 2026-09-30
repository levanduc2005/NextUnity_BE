package project.payment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.payment.dto.request.CreatePaymentRequest;
import project.payment.dto.response.CreatePaymentResponse;
import project.payment.dto.response.PaymentResponse;
import project.payment.dto.response.SubscriptionPlanResponse;
import vn.payos.model.webhooks.Webhook;

import java.util.List;

public interface PaymentService {

    CreatePaymentResponse createPayment(CreatePaymentRequest request);

    void handleWebhook(Webhook webhook);

    PaymentResponse verifyPaymentStatus(Long orderCode);

    List<SubscriptionPlanResponse> getAvailablePlans();

    Page<PaymentResponse> getMyPaymentHistory(Pageable pageable);
}
