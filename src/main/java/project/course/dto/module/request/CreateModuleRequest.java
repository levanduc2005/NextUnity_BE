package project.course.dto.module.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateModuleRequest(
        @NotBlank(message = "Tiêu đề chương học không được để trống")
        @Size(min = 2, max = 150, message = "Tiêu đề chương học từ 2 đến 150 ký tự")
        String title,

        @Size(max = 1000, message = "Mô tả chương học tối đa 1000 ký tự")
        String description
) {}
