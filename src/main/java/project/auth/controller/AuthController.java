package project.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.auth.dto.request.*;
import project.auth.dto.response.LoginResponse;
import project.auth.service.AuthService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.ratelimit.RateLimit;
import project.shared.ratelimit.RateLimitType;
import project.shared.response.ApiResponse;
import project.shared.util.CookieUtils;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CookieUtils cookieUtils;

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ApiResponse.success(
                null,
                "Registration successful. Please check your email for the OTP."
        );
    }

    @PostMapping("/verify-register-otp")
    public ApiResponse<Void> verifyRegisterOtp(
            @Valid @RequestBody VerifyOtpRequest request
    ) {
        authService.verifyRegisterOtp(request);
        return ApiResponse.success(
                null,
                "OTP verified successfully. Your account has been created."
        );
    }

    @RateLimit(limit = 5, durationInSeconds = 60, type = RateLimitType.IP)
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        LoginResponse loginResponse = authService.login(request);
        cookieUtils.setRefreshTokenCookie(response, loginResponse.refreshToken());
        return ApiResponse.success(loginResponse, "Login successful");
    }

    @PostMapping("/refresh")
    public ApiResponse<LoginResponse> refresh(
            @CookieValue(value = "refresh_token", required = false) String cookieRefreshToken,
            @RequestBody(required = false) RefreshTokenRequest request,
            HttpServletResponse response
    ) {
        String token = (request != null && request.refreshToken() != null && !request.refreshToken().isBlank())
                ? request.refreshToken()
                : cookieRefreshToken;
        if (token == null || token.isBlank()) {
            throw new AppException(ErrorCode.REFRESH_TOKEN_INVALID);
        }
        LoginResponse loginResponse = authService.refreshToken(token);
        cookieUtils.setRefreshTokenCookie(response, loginResponse.refreshToken());
        return ApiResponse.success(loginResponse, "Refresh Token successful");
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @CookieValue(value = "refresh_token", required = false) String cookieRefreshToken,
            @RequestBody(required = false) RefreshTokenRequest request,
            HttpServletResponse response
    ) {
        String token = (request != null && request.refreshToken() != null && !request.refreshToken().isBlank())
                ? request.refreshToken()
                : cookieRefreshToken;
        if (token != null && !token.isBlank()) {
            authService.logout(token);
        }
        cookieUtils.clearRefreshTokenCookie(response);
        return ApiResponse.success(
                null,
                "Logout successful"
        );
    }


    @RateLimit(limit = 1, durationInSeconds = 60, type = RateLimitType.IP)
    @PostMapping("/forgot-password")
    public ApiResponse<Void> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ) {
        authService.forgotPassword(request);

        return ApiResponse.success(
                null,
                "Please check your email for the OTP."
        );
    }

    @PostMapping("/verify-forgot-password-otp")
    public ApiResponse<Void> verifyForgotPasswordOtp(
            @Valid @RequestBody VerifyOtpRequest request
    ) {
        authService.verifyForgotPasswordOtp(request);
        return ApiResponse.success(
                null,
                "OTP verified successfully. You can change your password."
        );
    }

    @PostMapping("/reset-password")
    public ApiResponse<Void> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        authService.resetPassword(request);
        return ApiResponse.success(
                null,
                "Password reset successful. You can now login with your new password."
        );
    }
}
