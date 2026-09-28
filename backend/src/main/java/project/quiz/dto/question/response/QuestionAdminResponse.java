package project.quiz.dto.question.response;

import project.quiz.entity.enums.QuestionType;

import java.util.List;
import java.util.UUID;

public record QuestionAdminResponse(
        UUID id,
        String content,
        String explanation,
        QuestionType type,
        Double points,
        Integer orderIndex,
        List<QuestionOptionAdminResponse> options
) {}
