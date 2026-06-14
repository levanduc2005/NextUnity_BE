package project.identity.service;

import project.identity.dto.request.*;

public interface AuthService {

    void register(RegisterRequest request);

    void verifyRegisterOtp(VerifyOtpRequest request);

    LoginResult login(LoginRequest request);

    LoginResult refreshToken(String refreshToken);

    void logout(String refreshToken);

    void forgotPassword(ForgotPasswordRequest request);

    void verifyForgotPasswordOtp(VerifyOtpRequest request);

    void resetPassword(ResetPasswordRequest request);
}
