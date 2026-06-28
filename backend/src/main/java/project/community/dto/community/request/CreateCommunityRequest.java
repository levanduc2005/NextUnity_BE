package project.community.dto.community.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommunityRequest(
        @NotBlank(message = "Tên cộng đồng không được để trống")
        @Size(min = 3, max = 50, message = "Tên cộng đồng phải từ 3 đến 50 ký tự")
        String name,

        @NotBlank(message = "Mô tả không được để trống")
        @Size(max = 500, message = "Mô tả tối đa 500 ký tự")
        String description
) {}
