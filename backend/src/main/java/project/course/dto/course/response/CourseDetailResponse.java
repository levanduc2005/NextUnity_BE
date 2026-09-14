package project.course.dto.course.response;

import project.course.dto.module.response.ModuleResponse;
import project.course.entity.enums.CourseDifficulty;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CourseDetailResponse(
        UUID id,
        String title,
        String description,
        String category,
        CourseDifficulty difficulty,
        String thumbnailUrl,
        Integer totalLessons,
        UUID creatorId,
        String creatorName,
        Instant createdAt,
        List<ModuleResponse> modules
) {}
