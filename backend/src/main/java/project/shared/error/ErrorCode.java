package project.shared.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Authentication
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Không có quyền truy cập"),
    OTP_ALREADY_SENT(HttpStatus.BAD_REQUEST, "OTP đã được gửi, vui lòng thử lại sau"),

    // User
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "Email đã tồn tại"),

    //Mail
    CANNOT_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "Không thể gửi email");

    private final HttpStatus statusCode;
    private final String message;
}
