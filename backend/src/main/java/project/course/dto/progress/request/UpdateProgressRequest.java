package project.course.dto.progress.request;

import jakarta.validation.constraints.NotNull;
import project.course.entity.enums.LessonProgressStatus;

public record UpdateProgressRequest(
        @NotNull(message = "Trạng thái tiến độ bài học không được để trống")
        LessonProgressStatus status
) {}
