package project.payment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.payment.dto.request.CreatePaymentRequest;
import project.payment.dto.response.CreatePaymentResponse;
import project.payment.dto.response.PaymentResponse;
import project.payment.dto.response.SubscriptionPlanResponse;
import project.payment.service.PaymentService;
import project.shared.response.ApiResponse;
import vn.payos.model.webhooks.Webhook;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/plans")
    public ApiResponse<List<SubscriptionPlanResponse>> getAvailablePlans() {
        List<SubscriptionPlanResponse> plans = paymentService.getAvailablePlans();
        return ApiResponse.success(plans, "Lấy danh sách gói đăng ký thành công");
    }

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<CreatePaymentResponse> createPayment(
            @Valid @RequestBody CreatePaymentRequest request
    ) {
        CreatePaymentResponse response = paymentService.createPayment(request);
        return ApiResponse.success(response, "Tạo liên kết thanh toán thành công");
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody Webhook webhook) {
        paymentService.handleWebhook(webhook);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderCode}/verify")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<PaymentResponse> verifyPayment(@PathVariable Long orderCode) {
        PaymentResponse response = paymentService.verifyPaymentStatus(orderCode);
        return ApiResponse.success(response, "Kiểm tra và cập nhật trạng thái thanh toán thành công");
    }

    @GetMapping("/my-history")
    @PreAuthorize("isAuthenticated()")
    public ApiResponse<Page<PaymentResponse>> getMyPaymentHistory(Pageable pageable) {
        Page<PaymentResponse> response = paymentService.getMyPaymentHistory(pageable);
        return ApiResponse.success(response, "Lấy lịch sử thanh toán thành công");
    }
}
