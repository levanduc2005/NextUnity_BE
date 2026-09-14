package project.course.dto.course.response;

import project.course.entity.enums.CourseDifficulty;

import java.time.Instant;
import java.util.UUID;

public record CourseSummaryResponse(
        UUID id,
        String title,
        String description,
        String category,
        CourseDifficulty difficulty,
        String thumbnailUrl,
        Integer totalLessons,
        UUID creatorId,
        String creatorName,
        Instant createdAt
) {}
