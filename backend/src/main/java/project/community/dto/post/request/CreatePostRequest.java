package project.community.dto.post.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

public record CreatePostRequest(
        @NotNull(message = "Community ID không được để trống")
        UUID communityId,

        @NotBlank(message = "Tiêu đề không được để trống")
        @Size(min = 5, max = 150, message = "Tiêu đề từ 5 đến 150 ký tự")
        String title,

        @NotBlank(message = "Nội dung bài viết không được để trống")
        String content,

        Set<String> tagNames
) {}
