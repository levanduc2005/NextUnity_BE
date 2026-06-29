package project.community.dto.comment.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCommentRequest(
        @NotBlank(message = "Nội dung bình luận không được để trống")
        @Size(max = 1000, message = "Nội dung bình luận tối đa 1000 ký tự")
        String content
) {}
