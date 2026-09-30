package project.quiz.dto.quiz.response;

import project.quiz.dto.question.response.TakeQuestionResponse;

import java.util.List;
import java.util.UUID;

/**
 * DTO đề thi trả về cho học viên khi bắt đầu làm bài (Anti-Cheat).
 */
public record TakeQuizResponse(
        UUID id,
        UUID lessonId,
        String title,
        String description,
        Integer durationMinutes,
        Integer passingScore,
        Integer totalQuestions,
        List<TakeQuestionResponse> questions
) {}
