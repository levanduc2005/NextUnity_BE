package project.quiz.dto.question.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record ReorderQuestionsRequest(
        @NotEmpty(message = "Danh sách ID câu hỏi không được để trống")
        List<UUID> questionIds
) {}
