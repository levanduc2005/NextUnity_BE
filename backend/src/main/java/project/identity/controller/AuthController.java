package project.identity.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.identity.dto.request.*;
import project.identity.dto.response.LoginResponse;
import project.identity.service.AuthService;
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

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        // 1. authenticate user and generate access token + refresh token
        LoginResult result = authService.login(request);
        // 2. set refresh token in HttpOnly cookie
        cookieUtils.setRefreshTokenCookie(
                response,
                result.refreshToken()
        );
        return ApiResponse.success(
                result.response(),
                "Login successful"
        );
    }

    @PostMapping("/refresh")
    public ApiResponse<LoginResponse> refresh(
            @CookieValue("refresh_token") String refreshToken,
            HttpServletResponse response
    ) {
        // 1. refresh token is sent in HttpOnly cookie, so we get it from @CookieValue
        LoginResult result = authService.refreshToken(refreshToken);
        // 2. set refresh token in HttpOnly cookie
        cookieUtils.setRefreshTokenCookie(
                response,
                result.refreshToken()
        );
        return ApiResponse.success(
                result.response(),
                "Refresh Token successful"
        );
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @CookieValue("refresh_token") String refreshToken,
            HttpServletResponse response
    ) {
        // 1. revoke refresh token
        authService.logout(refreshToken);
        // 2. clear refresh token cookie
        cookieUtils.clearRefreshTokenCookie(response);
        return ApiResponse.success(
                null,
                "Logout successful"
        );
    }

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
