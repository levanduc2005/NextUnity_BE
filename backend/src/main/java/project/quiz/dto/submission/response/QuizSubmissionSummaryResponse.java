package project.quiz.dto.submission.response;

import java.time.Instant;
import java.util.UUID;

public record QuizSubmissionSummaryResponse(
        UUID id,
        UUID quizId,
        UUID userId,
        String userName,
        Double score,
        Double totalPoints,
        Double percentage,
        Boolean isPassed,
        Integer timeSpentSeconds,
        Instant createdAt
) {}
