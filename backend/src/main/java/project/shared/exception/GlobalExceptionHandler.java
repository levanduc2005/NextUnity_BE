package project.shared.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.shared.error.ErrorCode;
import project.shared.response.ApiResponse;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Exception bắt bằng AppException
    @ExceptionHandler(AppException.class)
    public ApiResponse<Void> handlingAppException(AppException ex){
        ErrorCode errorCode = ex.getErrorCode();
        HttpStatus status = errorCode.getStatusCode();

        return ApiResponse.failed(
                status.value(),
                errorCode.getMessage()
        );
    }
    // Bắt lỗi validation request body
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ApiResponse.failed(
                HttpStatus.BAD_REQUEST.value(),
                message
        );
    }

    // Bắt lỗi khi cookie bị thiếu
    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void>
    handleMissingCookie(
            MissingRequestCookieException ex
    ) {
        return ApiResponse.failed(
                HttpStatus.UNAUTHORIZED.value(),
                "cookie bị thiếu: " + ex.getCookieName()
        );
    }

    // Bắt lỗi khi request body không hợp lệ
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex
    ) {
        return ApiResponse.failed(
                HttpStatus.BAD_REQUEST.value(),
                "Yêu cầu không hợp lệ: " + ex.getMessage()
        );
    }

    // Bắt lỗi khi token hết hạn
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> handleExpiredJwtException(ExpiredJwtException ex) {
        return ApiResponse.failed(
                HttpStatus.UNAUTHORIZED.value(),
                "Token đã hết hạn, vui lòng đăng nhập lại"
        );
    }

    // Bắt lỗi khi token không hợp lệ hoặc đã bị thay đổi
    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> handleJwtException(JwtException ex) {
        return ApiResponse.failed(
                HttpStatus.UNAUTHORIZED.value(),
                "Token không hợp lệ hoặc đã bị thay đổi"
        );
    }

}
