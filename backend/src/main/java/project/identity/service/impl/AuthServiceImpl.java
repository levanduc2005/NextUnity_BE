package project.identity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.identity.dto.request.RegisterRequest;
import project.identity.dto.cache.RegisterCache;
import project.identity.service.AuthService;
import project.identity.util.OtpGenerator;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.mail.MailService;
import project.shared.redis.RedisKey;
import project.shared.redis.RedisService;
import project.user.service.UserService;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final RedisService redisService;
    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

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
}
