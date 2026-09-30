package project.quiz.dto.quiz.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateQuizRequest(
        @NotBlank(message = "Tiêu đề bài kiểm tra không được để trống")
        String title,

        String description,

        @NotNull(message = "Thời gian làm bài không được để trống")
        @Min(value = 1, message = "Thời gian làm bài tối thiểu là 1 phút")
        Integer durationMinutes,

        @NotNull(message = "Điểm qua môn không được để trống")
        @Min(value = 1, message = "Điểm qua môn tối thiểu là 1")
        @Max(value = 100, message = "Điểm qua môn tối đa là 100")
        Integer passingScore
) {
    public CreateQuizRequest {
        if (durationMinutes == null) {
            durationMinutes = 15;
        }
        if (passingScore == null) {
            passingScore = 80;
        }
    }
}
