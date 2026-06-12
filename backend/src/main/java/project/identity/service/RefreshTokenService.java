package project.identity.service;

public interface RefreshTokenService {
    void saveRefreshToken(String email, String refreshToken);
}
