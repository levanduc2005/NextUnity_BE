package project.course.dto.progress.response;

import project.course.entity.enums.LessonProgressStatus;

import java.time.Instant;
import java.util.UUID;

public record LessonProgressResponse(
        UUID id,
        UUID lessonId,
        String lessonTitle,
        LessonProgressStatus status,
        Instant completedAt,
        Integer courseProgressPercent
) {}
