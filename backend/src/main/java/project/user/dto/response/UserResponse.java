package project.user.dto.response;

import project.auth.dto.response.RoleResponse;

import java.time.Instant;

public record UserResponse(
        String id,
        String username,
        String email,
        Instant createdAt,
        RoleResponse role
) {
}
