package project.identity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Email
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 5, message = "Password must be at least 5 characters")
        String password,
        @NotBlank(message = "Username is required")
        String username
) {}