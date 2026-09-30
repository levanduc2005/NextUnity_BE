package project.quiz.dto.submission.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SubmitQuizRequest(
        @NotNull(message = "Thời gian làm bài không được để trống")
        @Min(value = 0, message = "Thời gian làm bài không được âm")
        Integer timeSpentSeconds,

        @Valid
        @NotNull(message = "Danh sách câu trả lời không được null")
        List<QuestionAnswerRequest> answers
) {
    public SubmitQuizRequest {
        if (timeSpentSeconds == null) {
            timeSpentSeconds = 0;
        }
        if (answers == null) {
            answers = List.of();
        }
    }
}
