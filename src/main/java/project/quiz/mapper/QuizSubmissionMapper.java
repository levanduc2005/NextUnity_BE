package project.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.quiz.dto.submission.response.QuizResultResponse;
import project.quiz.dto.submission.response.QuizSubmissionSummaryResponse;
import project.quiz.entity.QuizSubmission;

@Mapper(componentModel = "spring")
public interface QuizSubmissionMapper {

    @Mapping(target = "quizId", source = "quiz.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.fullName")
    @Mapping(
            target = "percentage",
            expression = "java(submission.getTotalPoints() != null && submission.getTotalPoints() > 0 ? ((double) submission.getScore() / submission.getTotalPoints()) * 100 : 0.0)"
    )
    QuizSubmissionSummaryResponse toSummaryResponse(QuizSubmission submission);

    @Mapping(target = "submissionId", source = "id")
    @Mapping(target = "quizId", source = "quiz.id")
    @Mapping(target = "questionResults", ignore = true)
    @Mapping(
            target = "percentage",
            expression = "java(submission.getTotalPoints() != null && submission.getTotalPoints() > 0 ? ((double) submission.getScore() / submission.getTotalPoints()) * 100 : 0.0)"
    )
    QuizResultResponse toResultResponse(QuizSubmission submission);
}
