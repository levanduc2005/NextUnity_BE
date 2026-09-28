package project.quiz.dto.question.response;

import java.util.UUID;

public record QuestionOptionAdminResponse(
        UUID id,
        String content,
        Boolean isCorrect,
        Integer orderIndex
) {}
