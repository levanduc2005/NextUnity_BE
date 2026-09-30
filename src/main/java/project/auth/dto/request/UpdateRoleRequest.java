package project.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateRoleRequest(
        @NotBlank
        String name,

        String description
) {
}
