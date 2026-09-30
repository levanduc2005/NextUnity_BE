package project.quiz.dto.submission.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record QuestionAnswerRequest(
        @NotNull(message = "ID câu hỏi không được để trống")
        UUID questionId,

        @NotEmpty(message = "Danh sách lựa chọn đã chọn không được để trống")
        List<UUID> selectedOptionIds
) {}
