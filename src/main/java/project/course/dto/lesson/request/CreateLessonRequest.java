package project.course.dto.lesson.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import project.course.entity.enums.LessonType;

public record CreateLessonRequest(
        @NotBlank(message = "Tiêu đề bài học không được để trống")
        @Size(min = 2, max = 200, message = "Tiêu đề bài học từ 2 đến 200 ký tự")
        String title,

        String content,

        @NotNull(message = "Thứ tự sắp xếp không được để trống")
        Integer orderIndex,

        @NotNull(message = "Loại bài học không được để trống")
        LessonType type,

        String videoUrl
) {}
