package project.auth.dto.response;

import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        String description
) {
}
