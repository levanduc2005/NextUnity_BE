package project.course.dto.course.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import project.course.entity.enums.CourseDifficulty;

public record CreateCourseRequest(
        @NotBlank(message = "Tiêu đề khóa học không được để trống")
        @Size(min = 3, max = 150, message = "Tiêu đề khóa học từ 3 đến 150 ký tự")
        String title,

        @Size(max = 2000, message = "Mô tả khóa học tối đa 2000 ký tự")
        String description,

        @NotBlank(message = "Danh mục không được để trống")
        String category,

        @NotNull(message = "Độ khó khóa học không được để trống")
        CourseDifficulty difficulty,

        String thumbnailUrl,

        Boolean isPremium
) {}
