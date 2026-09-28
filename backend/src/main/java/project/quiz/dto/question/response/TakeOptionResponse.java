package project.quiz.dto.question.response;

import java.util.UUID;

/**
 * DTO dành cho sinh viên làm bài - Tuyệt đối KHÔNG chứa trường `isCorrect`.
 */
public record TakeOptionResponse(
        UUID id,
        String content,
        Integer orderIndex
) {}
