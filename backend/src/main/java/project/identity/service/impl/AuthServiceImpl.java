package project.identity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.identity.dto.request.LoginRequest;
import project.identity.dto.request.LoginResult;
import project.identity.dto.request.RegisterRequest;
import project.identity.dto.cache.RegisterCache;
import project.identity.dto.request.VerifyOtpRequest;
import project.identity.dto.response.LoginResponse;
import project.identity.entity.Role;
import project.identity.repository.RoleRepository;
import project.identity.security.JwtService;
import project.identity.service.AuthService;
import project.identity.service.RefreshTokenService;
import project.identity.util.OtpGenerator;
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
        if (userService.existsByEmail(request.email())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXIST);
        }

        // 2. generate OTP
        String otp = OtpGenerator.generate6DigitOtp();

        // 3. encode password
        String encodedPassword = passwordEncoder.encode(request.password());

        // 4. save to Redis
        RegisterCache cache = RegisterCache.builder()
                .email(request.email())
                .password(encodedPassword)
                .username(request.username())
                .otp(otp)
                .build();

        String email = request.email().toLowerCase().trim();
        String key = "auth:register:" + email;

        if (redisService.exists(key)) {
            throw new AppException(ErrorCode.OTP_ALREADY_SENT);
        }

        redisService.set(RedisKey.REGISTER + email, cache, Duration.ofMinutes(5));

        // 5. send email
        try {
            mailService.sendOtp(email, otp);
        } catch (Exception ex) {
            log.error("Send mail failed", ex);
            redisService.delete(RedisKey.REGISTER + email);
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }

    @Override
    public void verifyOtp(VerifyOtpRequest request) {

        String email = EmailUtils.normalize(request.email());

        RegisterCache cache = redisService.get(
                RedisKey.REGISTER + email,
                RegisterCache.class
        );

        if (cache == null) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }

        if (!cache.otp().equals(request.otp())) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        User user = new User();
        user.setEmail(cache.email());
        user.setPasswordHash(cache.password());
        user.setUsername(cache.username());
        user.setRole(userRole);

        userService.saveUser(user);

        redisService.delete(RedisKey.REGISTER + email);
    }

    @Override
    public LoginResult login(LoginRequest request) {

        User user = userService.getUserByEmail(request.email());

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash()
        )) {
            throw new AppException(ErrorCode.PASSWORD_INCORRECT);
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        refreshTokenService.saveRefreshToken(user.getEmail(), refreshToken);

        return new LoginResult(
                new LoginResponse(accessToken,
                        userMapper.toResponse(user)),
                refreshToken
        );
    }
}
