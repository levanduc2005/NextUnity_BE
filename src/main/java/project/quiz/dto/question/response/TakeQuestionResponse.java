package project.quiz.dto.question.response;

import project.quiz.entity.enums.QuestionType;

import java.util.List;
import java.util.UUID;

/**
 * DTO câu hỏi dành cho sinh viên làm bài - Tuyệt đối KHÔNG chứa `explanation` hoặc `isCorrect`.
 */
public record TakeQuestionResponse(
        UUID id,
        String content,
        QuestionType type,
        Double points,
        Integer orderIndex,
        List<TakeOptionResponse> options
) {}
