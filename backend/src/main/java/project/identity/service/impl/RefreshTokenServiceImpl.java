package project.identity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.identity.config.JwtProperties;
import project.identity.entity.RefreshToken;
import project.identity.repository.RefreshTokenRepository;
import project.identity.service.RefreshTokenService;
import project.user.entity.User;
import project.user.service.UserService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserService userService;

    private final JwtProperties jwtProperties;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void saveRefreshToken(String email, String refreshToken) {
        User user = userService.getUserByEmail(email);

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiresAt(Instant.now().plus(jwtProperties.refreshTokenExpiration(), ChronoUnit.MILLIS))
                .isRevoked(false)
                .build();

        refreshTokenRepository.save(refreshTokenEntity);

    }
}
