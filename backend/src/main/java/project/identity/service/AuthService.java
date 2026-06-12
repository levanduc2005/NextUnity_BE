package project.identity.service;

import project.identity.dto.request.LoginRequest;
import project.identity.dto.request.LoginResult;
import project.identity.dto.request.RegisterRequest;
import project.identity.dto.request.VerifyOtpRequest;

public interface AuthService {
    void register(RegisterRequest request);
    void verifyOtp(VerifyOtpRequest request);
    LoginResult login(LoginRequest request);
}
