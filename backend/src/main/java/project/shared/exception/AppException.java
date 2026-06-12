package project.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.shared.error.ErrorCode;

@Getter
@Setter
@AllArgsConstructor
public class AppException extends RuntimeException{
    private final ErrorCode errorCode;
}
