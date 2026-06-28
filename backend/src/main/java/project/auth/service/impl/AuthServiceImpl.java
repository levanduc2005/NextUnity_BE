package project.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.auth.dto.cache.ForgotPasswordCache;
import project.auth.dto.request.*;
import project.auth.dto.cache.RegisterCache;
import project.auth.dto.response.LoginResponse;
import project.auth.entity.RefreshToken;
import project.auth.entity.Role;
import project.auth.repository.RoleRepository;
import project.auth.security.JwtService;
import project.auth.service.AuthService;
import project.auth.service.RefreshTokenService;
import project.auth.util.OtpGenerator;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.mail.MailService;
import project.shared.redis.RedisKey;
import project.shared.redis.RedisService;
import project.shared.util.EmailUtils;
import project.user.entity.User;
import project.user.mapper.UserMapper;
import project.user.service.UserService;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService {

    @Value("${otp.expiration.minute}")
    private int otpExpirationMinute;

    private final UserService userService;
    private final RedisService redisService;
    private final MailService mailService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    @Override
    public void register(RegisterRequest request) {
        // 1. check email tồn tại (USER MODULE)
        String email = EmailUtils.normalize(request.email());
        if (userService.existsByEmail(email)) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXIST);
        }
        // 2. generate OTP
        String otp = OtpGenerator.generate6DigitOtp();
        // 3. encode password
        String encodedPassword = passwordEncoder.encode(request.password());
        // 4. save to Redis
        RegisterCache cache = RegisterCache.builder()
                .email(email)
                .password(encodedPassword)
                .username(request.username())
                .otp(otp)
                .build();
        String key = RedisKey.REGISTER + email;
        // check if OTP already sent
        if (redisService.exists(key)) {
            throw new AppException(ErrorCode.OTP_ALREADY_SENT);
        }
        redisService.set(key, cache, Duration.ofMinutes(otpExpirationMinute));
        // 5. send email
        try {
            mailService.sendOtp(email, otp);
        } catch (Exception ex) {
            log.error("Send mail failed", ex);
            redisService.delete(key);
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }

    @Override
    public void verifyRegisterOtp(VerifyOtpRequest request) {
        // 1. normalize email
        String email = EmailUtils.normalize(request.email());
        // 2. get cache from Redis
        RegisterCache cache = redisService.get(
                RedisKey.REGISTER + email,
                RegisterCache.class
        );
        // 3. verify OTP
        if (cache == null) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }
        if (!cache.otp().equals(request.otp())) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }
        // 4. create user (USER MODULE)
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        User user = new User();
        user.setEmail(cache.email());
        user.setPasswordHash(cache.password());
        user.setUsername(cache.username());
        user.setRole(userRole);
        userService.saveUser(user);
        // 5. delete cache
        redisService.delete(RedisKey.REGISTER + email);
    }

    @Override
    public LoginResult login(LoginRequest request) {
        // 1. get user by email
        User user = userService.getUserByEmail(request.email());
        // 2. verify password
        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash()
        )) {
            throw new AppException(ErrorCode.PASSWORD_INCORRECT);
        }
        // 3. generate access token + refresh token
        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.create(user);
        // 4. return tokens
        return new LoginResult(
                new LoginResponse(accessToken,
                        userMapper.toResponse(user)),
                refreshToken.getToken()
        );
    }

    @Override
    public LoginResult refreshToken(String refreshToken) {
        // 1. verify refresh token
        RefreshToken currentRefreshToken = refreshTokenService.verify(refreshToken);
        User user = currentRefreshToken.getUser();
        // 2. generate new access token + refresh token
        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken newRefreshToken = refreshTokenService.create(user);
        // 3. revoke old refresh token
        refreshTokenService.revoke(currentRefreshToken.getToken());
        // 4. return new tokens
        return new LoginResult(
                new LoginResponse(accessToken,
                        userMapper.toResponse(user)),
                newRefreshToken.getToken()
        );
    }

    @Override
    public void logout(String refreshToken) {
        // revoke refresh token
        refreshTokenService.revoke(refreshToken);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        // 1. get user by email
        User user = userService.getUserByEmail(request.email());
        // 2. generate OTP
        String otp = OtpGenerator.generate6DigitOtp();
        // check if OTP already sent
        String key = RedisKey.FORGOT_PASSWORD + user.getEmail();
        if (redisService.exists(key)) {
            throw new AppException(ErrorCode.OTP_ALREADY_SENT);
        }
        // 3. save OTP to Redis
        ForgotPasswordCache cache = ForgotPasswordCache.builder()
                .otp(otp)
                .verified(false)
                .build();
        redisService.set(key, cache, Duration.ofMinutes(otpExpirationMinute));
        // 5. send email
        try {
            mailService.sendOtp(user.getEmail(), otp);
        } catch (Exception ex) {
            log.error("Send mail failed", ex);
            redisService.delete(key);
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }

    @Override
    public void verifyForgotPasswordOtp(VerifyOtpRequest request) {
        // 1. normalize email
        String email = EmailUtils.normalize(request.email());
        // 2. get cache from Redis
        ForgotPasswordCache cache = redisService.get(
                RedisKey.FORGOT_PASSWORD + email,
                ForgotPasswordCache.class
        );
        // 3. verify OTP
        if (cache == null) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }
        if (!cache.otp().equals(request.otp())) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }
        // 4. mark OTP as verified
        cache = ForgotPasswordCache.builder()
                .otp(cache.otp())
                .verified(true)
                .build();
        // 5. update cache in Redis
        redisService.set(RedisKey.FORGOT_PASSWORD + email, cache, Duration.ofMinutes(otpExpirationMinute));
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        // 1. get user by email
        User user = userService.getUserByEmail(request.email());
        // 2. get cache from Redis
        String key = RedisKey.FORGOT_PASSWORD + user.getEmail();
        ForgotPasswordCache cache = redisService.get(
                key,
                ForgotPasswordCache.class
        );
        // 3. check if OTP is verified
        if (cache == null || !cache.verified()) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }
        // 4. encode new password
        String encodedPassword = passwordEncoder.encode(request.newPassword());
        // 5. update password (USER MODULE)
        user.setPasswordHash(encodedPassword);
        userService.saveUser(user);
        // 6. delete cache
        redisService.delete(key);
    }
}
