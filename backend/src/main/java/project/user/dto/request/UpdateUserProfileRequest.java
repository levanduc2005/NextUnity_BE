package project.user.dto.request;

import java.time.LocalDate;

public record UpdateUserProfileRequest(
        String name,
        String university,
        String studentId,
        String avatarUrl,
        String gender,
        LocalDate dateOfBirth,
        Integer cohort,
        String major,
        String bio
) {
}
