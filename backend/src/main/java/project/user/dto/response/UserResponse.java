package project.user.dto.response;

import project.identity.dto.response.RoleResponse;

import java.time.Instant;

public record UserResponse(
        String id,
        String username,
        String email,
        Instant createdAt,
        RoleResponse role
) {
}
