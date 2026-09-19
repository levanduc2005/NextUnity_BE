package project.course.dto.mindmap.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SaveMindmapRequest(
        @NotBlank(message = "Tiêu đề sơ đồ không được để trống")
        @Size(min = 2, max = 200, message = "Tiêu đề từ 2 đến 200 ký tự")
        String title,

        @NotBlank(message = "Dữ liệu sơ đồ không được để trống")
        String data
) {}
