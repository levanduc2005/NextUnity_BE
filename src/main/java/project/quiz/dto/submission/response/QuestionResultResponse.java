package project.quiz.dto.submission.response;

import java.util.List;
import java.util.UUID;

public record QuestionResultResponse(
                UUID questionId,
                String content,
                String explanation,
                Double points,
                Double earnedPoints,
                List<UUID> selectedOptionIds,
                List<UUID> correctOptionIds,
                Boolean isCorrect) {
}
