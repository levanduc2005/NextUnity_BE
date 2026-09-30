package project.shared.mail;

public interface MailService {
    void sendOtp(String email, String otp);
}
