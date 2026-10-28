package project.community.dto.post.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record CreatePostRequest(
        @NotBlank(message = "Tiêu đề không được để trống")
        @Size(min = 5, max = 150, message = "Tiêu đề từ 5 đến 150 ký tự")
        String title,

        @NotBlank(message = "Nội dung bài viết không được để trống")
        String content,

        Set<String> tagNames
) {}
