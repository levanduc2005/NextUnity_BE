package project.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
        @Email(message = "Invalid email format")
        String email,
        @NotBlank(message = "Password must not be blank")
        String newPassword
) {
}
