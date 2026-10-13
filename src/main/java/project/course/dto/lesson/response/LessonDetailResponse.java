package project.course.dto.lesson.response;

import project.course.entity.enums.LessonType;

import java.time.Instant;
import java.util.UUID;

public record LessonDetailResponse(
        UUID id,
        UUID moduleId,
        String title,
        String content,
        Integer orderIndex,
        LessonType type,
        String typeName,
        Integer estimatedMinutes,
        String videoUrl,
        Instant createdAt
) {}
