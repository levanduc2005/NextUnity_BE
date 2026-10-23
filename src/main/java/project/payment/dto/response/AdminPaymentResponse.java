package project.payment.dto.response;

import project.payment.entity.enums.PaymentStatus;
import project.payment.entity.enums.SubscriptionPlan;

import java.time.Instant;
import java.util.UUID;

public record AdminPaymentResponse(
        UUID id,
        Long orderCode,
        Long amount,
        PaymentStatus status,
        SubscriptionPlan plan,
        String planName,
        UUID userId,
        String userFullName,
        String userEmail,
        String userAvatarUrl,
        Instant expiresAt,
        Instant createdAt,
        Instant updatedAt
) {}
