package project.payment.dto.response;

import project.payment.entity.enums.SubscriptionPlan;

import java.time.Instant;

public record CreatePaymentResponse(
        Long orderCode,
        String checkoutUrl,
        String qrCode,
        Long amount,
        SubscriptionPlan plan,
        Instant expiresAt
) {}
