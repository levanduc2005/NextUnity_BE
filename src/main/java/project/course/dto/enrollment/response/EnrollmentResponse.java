package project.course.dto.enrollment.response;

import project.course.entity.enums.CourseDifficulty;
import project.course.entity.enums.EnrollmentStatus;

import java.time.Instant;
import java.util.UUID;

public record EnrollmentResponse(
        UUID id,
        UUID courseId,
        String courseCode,
        String courseTitle,
        String courseDescription,
        String courseThumbnailUrl,
        CourseDifficulty courseDifficulty,
        Integer totalLessons,
        Integer progressPercent,
        EnrollmentStatus status,
        Instant createdAt,
        Instant completedAt
) {}
