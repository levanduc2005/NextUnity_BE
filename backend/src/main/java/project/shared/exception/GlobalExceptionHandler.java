package project.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project.shared.error.ErrorCode;
import project.shared.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Exception bắt bằng AppException
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<Void>> handlingAppException(AppException ex){
        ErrorCode errorCode = ex.getErrorCode();
        HttpStatus status = errorCode.getStatusCode();
        return ResponseEntity
                .status(status)
                .body(ApiResponse.failed(
                        status.value(),
                        errorCode.getMessage())
                );
    }
}
