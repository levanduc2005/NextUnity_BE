package project.auth.service;

import project.auth.dto.request.*;
import project.auth.dto.response.LoginResponse;

public interface AuthService {

    void register(RegisterRequest request);

    void verifyRegisterOtp(VerifyOtpRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(String refreshToken);

    void logout(String refreshToken);

    void forgotPassword(ForgotPasswordRequest request);

    void verifyForgotPasswordOtp(VerifyOtpRequest request);

    void resetPassword(ResetPasswordRequest request);

    LoginResponse loginWithGoogle(GoogleLoginRequest request);

    void resendRegisterOtp(SendOtpRequest request);
}
