package project.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CheckPasswordRequest(
        @NotBlank(message = "Mật khẩu hiện tại không được để trống")
        String password
) {
}
