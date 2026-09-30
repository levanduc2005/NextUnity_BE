package project.user.dto.response;

import project.auth.dto.response.RoleResponse;
import project.user.entity.enums.SubscriptionTier;

import java.time.Instant;
import java.time.LocalDate;

public record UserResponse(
        String id,
        String name,
        String username,
        String email,
        String university,
        String studentId,
        String avatarUrl,
        Integer points,
        Integer streak,
        String gender,
        LocalDate dateOfBirth,
        Integer cohort,
        String major,
        String bio,
        Instant createdAt,
        RoleResponse role,
        SubscriptionTier subscriptionTier,
        Instant premiumUntil,
        Boolean isPlus
) {
}

