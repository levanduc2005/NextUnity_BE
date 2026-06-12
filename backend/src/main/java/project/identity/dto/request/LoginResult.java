package project.identity.dto.request;

import project.identity.dto.response.LoginResponse;

public record LoginResult(
        LoginResponse response,
        String refreshToken
) {}
