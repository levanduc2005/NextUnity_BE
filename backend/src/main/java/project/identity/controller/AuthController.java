package project.identity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.identity.dto.request.RegisterRequest;
import project.identity.dto.request.VerifyOtpRequest;
import project.identity.service.AuthService;
import project.shared.response.ApiResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

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
}
