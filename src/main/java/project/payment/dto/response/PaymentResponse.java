package project.payment.dto.response;

import project.payment.entity.enums.PaymentStatus;
import project.payment.entity.enums.SubscriptionPlan;

import java.time.Instant;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        Long orderCode,
        Long amount,
        PaymentStatus status,
        SubscriptionPlan plan,
        String checkoutUrl,
        String qrCode,
        Instant createdAt
) {}
