package project.quiz.dto.quiz.response;

import project.quiz.dto.question.response.QuestionAdminResponse;
import project.quiz.entity.enums.QuizStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record QuizAdminResponse(
        UUID id,
        UUID lessonId,
        String title,
        String description,
        Integer durationMinutes,
        Integer passingScore,
        Integer totalQuestions,
        QuizStatus status,
        Instant createdAt,
        Instant updatedAt,
        List<QuestionAdminResponse> questions
) {}
