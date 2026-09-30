package project.payment.dto.request;

import jakarta.validation.constraints.NotNull;
import project.payment.entity.enums.SubscriptionPlan;

public record CreatePaymentRequest(
        @NotNull(message = "Gói đăng ký không được để trống")
        SubscriptionPlan plan
) {}
