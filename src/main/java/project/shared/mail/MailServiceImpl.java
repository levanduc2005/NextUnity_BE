package project.shared.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${brevo.api-key:}")
    private String apiKey;

    @Value("${brevo.sender-email:le4035040@gmail.com}")
    private String senderEmail;

    @Value("${brevo.sender-name:NextUnity}")
    private String senderName;

    private static final String BREVO_EMAIL_URL = "https://api.brevo.com/v3/smtp/email";

    @Override
    public void sendOtp(String email, String otp) {
        if (apiKey == null || apiKey.isBlank()) {
            log.error("Brevo API key is not configured. Cannot send email to: {}", email);
            throw new IllegalStateException("Brevo API key is not configured");
        }

        try {
            Map<String, Object> payload = Map.of(
                    "sender", Map.of(
                            "name", senderName,
                            "email", senderEmail),
                    "to", List.of(
                            Map.of("email", email)),
                    "subject", "Email Verification - NextUnity",
                    "htmlContent", String.format(
                            "<div style=\"font-family: Arial, sans-serif; padding: 24px; color: #333; max-width: 500px; margin: 0 auto; border: 1px solid #e0e0e0; border-radius: 12px;\">"
                                    + "<h2 style=\"color: #0066FF; margin-top: 0;\">NextUnity Verification</h2>"
                                    + "<p>Xin chào,</p>"
                                    + "<p>Mã xác thực (OTP) của bạn là:</p>"
                                    + "<div style=\"font-size: 32px; font-weight: bold; letter-spacing: 6px; color: #0066FF; background: #F0F7FF; padding: 14px 20px; border-radius: 8px; text-align: center; margin: 20px 0;\">%s</div>"
                                    + "<p style=\"color: #666; font-size: 14px;\">Mã này có hiệu lực trong vòng 5 phút. Vui lòng không chia sẻ mã này cho bất kỳ ai.</p>"
                                    + "<hr style=\"border: none; border-top: 1px solid #eee; margin: 20px 0;\" />"
                                    + "<p style=\"color: #999; font-size: 12px; margin-bottom: 0;\">Đội ngũ phát triển NextUnity</p>"
                                    + "</div>",
                            otp),
                    "textContent",
                    String.format("Mã xác thực NextUnity của bạn là: %s. Mã có hiệu lực trong 5 phút.", otp));

            String requestBody = objectMapper.writeValueAsString(payload);

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BREVO_EMAIL_URL))
                    .header("accept", "application/json")
                    .header("api-key", apiKey)
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .timeout(Duration.ofSeconds(15))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                log.info("Successfully sent OTP email via Brevo API to: {}, response: {}", email, response.body());
            } else {
                log.error("Failed to send OTP email via Brevo to: {}. Status: {}, Response: {}", email,
                        response.statusCode(), response.body());
                throw new RuntimeException("Brevo API error: " + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            log.error("Exception occurred while sending OTP email to: {}", email, e);
            throw new RuntimeException("Error sending email via Brevo", e);
        }
    }
}
