package project.identity.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.identity.config.JwtProperties;
import project.identity.dto.request.LoginRequest;
import project.identity.dto.request.LoginResult;
import project.identity.dto.request.RegisterRequest;
import project.identity.dto.request.VerifyOtpRequest;
import project.identity.dto.response.LoginResponse;
import project.identity.service.AuthService;
import project.shared.response.ApiResponse;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtProperties jwtProperties;

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ApiResponse.success(
                null,
                "Registration successful. Please check your email for the OTP."
        );
    }

    @PostMapping("/verify-otp")
    public ApiResponse<Void> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request
    ) {
        authService.verifyOtp(request);
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

        LoginResult result = authService.login(request);

        ResponseCookie cookie = ResponseCookie
                .from("refresh_token", result.refreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofMillis(jwtProperties.refreshTokenExpiration()))
                .build();

        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );

        return ApiResponse.success(
                result.response(),
                "Login successful"
        );
    }
}
