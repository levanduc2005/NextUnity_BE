package project.identity.security;

import project.user.entity.User;

public interface JwtService {
    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String extractEmail(String token);

    boolean isValid(String token);
}
