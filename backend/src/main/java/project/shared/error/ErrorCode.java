package project.shared.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Authentication
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Không có quyền truy cập"),
    // OTP
    OTP_ALREADY_SENT(HttpStatus.BAD_REQUEST, "OTP đã được gửi, vui lòng thử lại sau"),
    OTP_EXPIRED(HttpStatus.BAD_REQUEST, "OTP expired"),
    INVALID_OTP(HttpStatus.BAD_REQUEST, "Invalid OTP"),
    // Login
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "Password không chính xác"),
    // Refresh token
    REFRESH_TOKEN_INVALID(HttpStatus.BAD_REQUEST, "Refresh token không hợp lệ"),
    REFRESH_TOKEN_REVOKED(HttpStatus.BAD_REQUEST, "Refresh token đã bị thu hồi"),
    REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "Refresh token đã hết hạn"),
    // Role
    ROLE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Role đã tồn tại"),
    ROLE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found"),
    // User
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "Email đã tồn tại"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User không tồn tại"),
    // Mail
    CANNOT_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "Không thể gửi email"),
    // Community
    COMMUNITY_NAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Tên cộng đồng đã tồn tại"),
    COMMUNITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Cộng đồng không tồn tại");

    private final HttpStatus statusCode;
    private final String message;
}
