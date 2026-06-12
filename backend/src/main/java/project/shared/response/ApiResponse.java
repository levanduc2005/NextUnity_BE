package project.shared.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    // Helper methods for Success
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(200, message, data);
    }
    // Helper methods for Failure
    public static ApiResponse<Void> failed(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}