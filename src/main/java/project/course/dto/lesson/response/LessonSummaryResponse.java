package project.course.dto.lesson.response;

import project.course.entity.enums.LessonType;

import java.util.UUID;

public record LessonSummaryResponse(
        UUID id,
        String title,
        Integer orderIndex,
        LessonType type,
        String typeName,
        Integer estimatedMinutes,
        Boolean isCompleted,
        UUID quizId
) {}
