package project.shared.mail;

public interface MailService {
    void sendOtp(String email, String otp);

    void sendWelcomeGoogleAccount(String email, String fullName, String rawPassword);
}
