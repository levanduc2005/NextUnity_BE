package project.identity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.identity.config.JwtProperties;
import project.identity.entity.RefreshToken;
import project.identity.repository.RefreshTokenRepository;
import project.identity.security.JwtService;
import project.identity.service.RefreshTokenService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.user.entity.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final JwtService jwtService;

    private final JwtProperties jwtProperties;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken create(User user) {
        // 1. generate refresh token
        String refreshToken = jwtService.generateRefreshToken(user);
        // 2. lưu refresh token vào database
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiresAt(Instant.now().plus(jwtProperties.refreshTokenExpiration(), ChronoUnit.MILLIS))
                .isRevoked(false)
                .build();
        // 3. trả về refresh token
        return refreshTokenRepository.save(refreshTokenEntity);
    }

    @Override
    public RefreshToken verify(String token) {
        // 1. check token tồn tại
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new AppException(ErrorCode.REFRESH_TOKEN_INVALID));
        // 2. check token đã bị thu hồi hay chưa
        if(Boolean.TRUE.equals(refreshToken.getIsRevoked())){
            throw new AppException(ErrorCode.REFRESH_TOKEN_REVOKED);
        }
        // 3. check token đã hết hạn hay chưa
        if(refreshToken.getExpiresAt().isBefore(Instant.now())){
            throw new AppException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        // 4. nếu hợp lệ thì trả về refresh token
        return refreshToken;
    }

    @Override
    public void revoke(String token) {
        // 1. check token tồn tại
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new AppException(ErrorCode.REFRESH_TOKEN_INVALID));
        // 2. đánh dấu token là đã bị thu hồi
        refreshToken.setIsRevoked(true);
    }
}
