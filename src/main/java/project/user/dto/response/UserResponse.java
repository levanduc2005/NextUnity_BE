package project.user.dto.response;

import project.user.entity.enums.SubscriptionTier;
import project.user.entity.enums.UserGender;

import java.time.Instant;
import java.time.LocalDate;

public record UserResponse(
        String id,
        String fullName,
        String email,
        String university,
        String studentId,
        String avatarUrl,
        Integer streak,
        UserGender gender,
        String genderName,
        LocalDate dateOfBirth,
        Integer cohort,
        String major,
        String bio,
        Instant createdAt,
        String role,
        SubscriptionTier subscriptionTier,
        Instant premiumUntil,
        Boolean isPlus
) {
}
