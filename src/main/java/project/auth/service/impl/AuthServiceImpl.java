package project.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.auth.dto.cache.ForgotPasswordCache;
import project.auth.dto.request.*;
import project.auth.dto.cache.RegisterCache;
import project.auth.dto.response.LoginResponse;
import project.auth.entity.RefreshToken;
import project.auth.entity.Role;
import project.auth.repository.RoleRepository;
import project.auth.security.GoogleTokenVerifier;
import project.auth.security.JwtService;
import project.auth.service.AuthService;
import project.auth.service.RefreshTokenService;
import project.auth.util.OtpGenerator;
import project.auth.util.PasswordGenerator;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.mail.MailService;
import project.shared.redis.RedisKey;
import project.shared.redis.RedisService;
import project.shared.util.AvatarUtils;
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
    private final GoogleTokenVerifier googleTokenVerifier;

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

        String key = RedisKey.REGISTER + email;
        RegisterCache existingCache = redisService.get(key, RegisterCache.class);

        // 2. Dùng lại mã OTP cũ nếu còn hạn trong Redis, nếu chưa có hoặc đã hết hạn thì sinh mã mới
        String otp = (existingCache != null)
                ? existingCache.otp()
                : OtpGenerator.generate6DigitOtp();

        // 3. encode password mới nhất
        String encodedPassword = passwordEncoder.encode(request.password());

        // 4. Lưu / cập nhật cache Redis với thông tin họ tên & mật khẩu mới nhất
        RegisterCache cache = RegisterCache.builder()
                .email(email)
                .password(encodedPassword)
                .fullName(request.fullName())
                .otp(otp)
                .build();
        redisService.set(key, cache, Duration.ofMinutes(otpExpirationMinute));

        // 5. Luôn gửi email để người dùng nhận được thông báo mới nhất trong hòm thư
        try {
            mailService.sendOtp(email, otp);
        } catch (Exception ex) {
            log.error("Send mail failed", ex);
            redisService.delete(key);
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }

    @Override
    @Transactional
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
        Role userRole = roleRepository.findByName("STUDENT")
                .or(() -> roleRepository.findByName("USER"))
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        User user = new User();
        user.setEmail(cache.email());
        user.setPasswordHash(cache.password());
        user.setFullName(cache.fullName());
        user.setRole(userRole);
        if (user.getAvatarUrl() == null || user.getAvatarUrl().isBlank()) {
            user.setAvatarUrl(AvatarUtils.generateDefaultAvatar(user.getFullName()));
        }
        userService.saveUser(user);
        // 5. delete cache
        redisService.delete(RedisKey.REGISTER + email);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
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
        return new LoginResponse(
                accessToken,
                refreshToken.getToken(),
                userMapper.toResponse(user)
        );
    }

    @Override
    @Transactional
    public LoginResponse refreshToken(String refreshToken) {
        // 1. verify refresh token
        RefreshToken currentRefreshToken = refreshTokenService.verify(refreshToken);
        User user = currentRefreshToken.getUser();
        // 2. generate new access token + refresh token
        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken newRefreshToken = refreshTokenService.create(user);
        // 3. revoke old refresh token
        refreshTokenService.revoke(currentRefreshToken.getToken());
        // 4. return new tokens
        return new LoginResponse(
                accessToken,
                newRefreshToken.getToken(),
                userMapper.toResponse(user)
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
        String key = RedisKey.FORGOT_PASSWORD + user.getEmail();
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

    @Override
    @Transactional
    public LoginResponse loginWithGoogle(GoogleLoginRequest request) {
        // 1. Xác thực Google ID Token với Google
        GoogleIdToken.Payload payload = googleTokenVerifier.verify(request.idToken());
        String email = EmailUtils.normalize(payload.getEmail());
        String name = (String) payload.get("name");
        if (name == null || name.isBlank()) {
            name = email.split("@")[0];
        }

        User user;
        // 2. Kiểm tra tài khoản đã tồn tại hay chưa
        if (userService.existsByEmail(email)) {
            user = userService.getUserByEmail(email);
        } else {
            // 3. Tài khoản mới: Khởi tạo với mật khẩu ngẫu nhiên
            Role studentRole = roleRepository.findByName("STUDENT")
                    .or(() -> roleRepository.findByName("USER"))
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

            String rawPassword = PasswordGenerator.generateRandomPassword();
            String picture = (String) payload.get("picture");

            user = new User();
            user.setEmail(email);
            user.setFullName(name);
            user.setPasswordHash(passwordEncoder.encode(rawPassword));
            user.setRole(studentRole);
            if (picture != null && !picture.isBlank()) {
                user.setAvatarUrl(picture);
            } else {
                user.setAvatarUrl(AvatarUtils.generateDefaultAvatar(name));
            }
            userService.saveUser(user);

            // 4. Gửi email chào mừng kèm mật khẩu khởi tạo về hòm thư người dùng
            try {
                mailService.sendWelcomeGoogleAccount(email, name, rawPassword);
            } catch (Exception ex) {
                log.error("Failed to send welcome Google email to {}: {}", email, ex.getMessage());
            }
        }

        // 5. Sinh cặp Access Token & Refresh Token của NextUnity
        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.create(user);

        return new LoginResponse(
                accessToken,
                refreshToken.getToken(),
                userMapper.toResponse(user)
        );
    }

    @Override
    public void resendRegisterOtp(SendOtpRequest request) {
        // 1. Chuẩn hóa email
        String email = EmailUtils.normalize(request.email());

        // 2. Kiểm tra phiên đăng ký tài khoản mới đang chờ xác thực OTP
        String registerKey = RedisKey.REGISTER + email;
        RegisterCache registerCache = redisService.get(registerKey, RegisterCache.class);
        if (registerCache == null) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }

        // 3. Tạo mã OTP mới và cập nhật lại cache Redis
        String newOtp = OtpGenerator.generate6DigitOtp();
        RegisterCache updatedCache = RegisterCache.builder()
                .email(registerCache.email())
                .password(registerCache.password())
                .fullName(registerCache.fullName())
                .otp(newOtp)
                .build();
        redisService.set(registerKey, updatedCache, Duration.ofMinutes(otpExpirationMinute));

        // 4. Gửi mã OTP mới tới email người dùng
        try {
            mailService.sendOtp(email, newOtp);
        } catch (Exception ex) {
            log.error("Send register OTP mail failed for {}", email, ex);
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
