package project.course.dto.slide.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UploadSlideRequest(
        @NotBlank(message = "Tên tài liệu không được để trống")
        @Size(min = 2, max = 255, message = "Tên tài liệu từ 2 đến 255 ký tự")
        String fileName,

        @NotBlank(message = "Đường dẫn file không được để trống")
        String fileUrl
) {}
