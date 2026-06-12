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
    OTP_EXPIRED(HttpStatus.BAD_REQUEST, "OTP expired"),
    INVALID_OTP(HttpStatus.BAD_REQUEST, "Invalid OTP"),
    ROLE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found"),

    // Role
    ROLE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Role đã tồn tại"),

    // User
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "Email đã tồn tại"),

    // Mail
    CANNOT_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "Không thể gửi email");



    private final HttpStatus statusCode;
    private final String message;
}
