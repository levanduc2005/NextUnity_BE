package project.course.dto.module.response;

import project.course.dto.lesson.response.LessonSummaryResponse;

import java.util.List;
import java.util.UUID;

public record ModuleResponse(
        UUID id,
        UUID courseId,
        String title,
        String description,
        Integer orderIndex,
        Boolean isLocked,
        List<LessonSummaryResponse> lessons
) {}
