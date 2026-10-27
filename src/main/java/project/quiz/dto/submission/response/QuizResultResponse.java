package project.quiz.dto.submission.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record QuizResultResponse(
                UUID submissionId,
                UUID quizId,
                Double score,
                Double totalPoints,
                Double percentage,
                Boolean isPassed,
                Integer timeSpentSeconds,
                Instant submittedAt,
                List<QuestionResultResponse> questionResults) {
}
