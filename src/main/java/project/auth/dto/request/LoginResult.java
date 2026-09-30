package project.auth.dto.request;

import project.auth.dto.response.LoginResponse;

public record LoginResult(
        LoginResponse response,
        String refreshToken
) {}
