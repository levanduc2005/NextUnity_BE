package project.user.dto.request;

import project.user.entity.enums.UserGender;

import java.time.LocalDate;

public record UpdateUserProfileRequest(
        String fullName,
        String university,
        String studentId,
        String avatarUrl,
        UserGender gender,
        LocalDate dateOfBirth,
        Integer cohort,
        String major,
        String bio
) {
}
