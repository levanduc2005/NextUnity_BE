package project.auth.dto.cache;

import lombok.Builder;

@Builder
public record RegisterCache(
        String email,
        String password,
        String username,
        String otp
) {}
