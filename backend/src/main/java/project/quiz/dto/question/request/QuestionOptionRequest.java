package project.quiz.dto.question.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record QuestionOptionRequest(
        /**
         * ID của lựa chọn. Để null khi tạo mới hoặc khi thêm lựa chọn mới lúc cập nhật câu hỏi.
         */
        UUID id,

        @NotBlank(message = "Nội dung lựa chọn không được để trống")
        String content,

        @NotNull(message = "Phải xác định lựa chọn này là đúng hay sai")
        Boolean isCorrect
) {}
