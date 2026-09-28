package project.quiz.dto.question.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import project.quiz.entity.enums.QuestionType;

import java.util.List;

public record UpdateQuestionRequest(
        @NotBlank(message = "Nội dung câu hỏi không được để trống")
        String content,

        String explanation,

        @NotNull(message = "Loại câu hỏi không được để trống")
        QuestionType type,

        Double points,

        @Valid
        @NotEmpty(message = "Câu hỏi phải có ít nhất 2 lựa chọn")
        @Size(min = 2, message = "Câu hỏi phải có ít nhất 2 lựa chọn")
        List<QuestionOptionRequest> options
) {
    public UpdateQuestionRequest {
        if (options == null) {
            options = List.of();
        }
    }
}
