package project.payment.dto.response;

public record SubscriptionPlanResponse(
        String code,
        String name,
        Long price,
        Integer durationDays,
        String description
) {}
