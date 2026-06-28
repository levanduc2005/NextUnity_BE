package project.community.dto.community.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCommunityRequest(
        @NotBlank(message = "Mô tả không được để trống")
        @Size(max = 500, message = "Mô tả tối đa 500 ký tự")
        String description
) {}

