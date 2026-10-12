package project.course.dto.course.response;

import project.course.entity.enums.CourseCategory;
import project.course.entity.enums.CourseDifficulty;

import java.time.Instant;
import java.util.UUID;

public record CourseSummaryResponse(
        UUID id,
        String code,
        String title,
        String description,
        CourseCategory category,
        String categoryName,
        CourseDifficulty difficulty,
        String thumbnailUrl,
        Integer totalLessons,
        Boolean isPremium,
        UUID creatorId,
        String creatorName,
        Instant createdAt
) {}
