package project.identity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VerifyOtpRequest(
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "OTP is required")
        String otp
) {
}
