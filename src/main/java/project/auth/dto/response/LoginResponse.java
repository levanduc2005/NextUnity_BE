package project.auth.dto.response;

import project.user.dto.response.UserResponse;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        UserResponse user
) {
}
