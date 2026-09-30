package project.quiz.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.course.dto.progress.request.UpdateProgressRequest;
import project.course.entity.enums.LessonProgressStatus;
import project.course.service.EnrollmentService;
import project.course.validator.CourseAccessValidator;
import project.quiz.dto.submission.request.QuestionAnswerRequest;
import project.quiz.dto.submission.request.SubmitQuizRequest;
import project.quiz.dto.submission.response.QuestionResultResponse;
import project.quiz.dto.submission.response.QuizResultResponse;
import project.quiz.dto.submission.response.QuizSubmissionSummaryResponse;
import project.quiz.entity.*;
import project.quiz.entity.enums.QuizStatus;
import project.quiz.mapper.QuizSubmissionMapper;
import project.quiz.repository.QuizRepository;
import project.quiz.repository.QuizSubmissionOptionRepository;
import project.quiz.repository.QuizSubmissionRepository;
import project.quiz.service.QuizSubmissionService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.entity.User;
import project.user.service.UserService;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizSubmissionServiceImpl implements QuizSubmissionService {

    private final UserService userService;

    private final QuizRepository quizRepository;
    private final QuizSubmissionRepository quizSubmissionRepository;
    private final QuizSubmissionOptionRepository quizSubmissionOptionRepository;

    private final CourseAccessValidator courseAccessValidator;
    private final QuizSubmissionMapper quizSubmissionMapper;
    private final EnrollmentService enrollmentService;

    @Override
    @Transactional
    public QuizResultResponse submitQuiz(UUID quizId, SubmitQuizRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
                () -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        courseAccessValidator.validateAccessByLessonId(quiz.getLesson().getId());

        if (quiz.getStatus() != QuizStatus.PUBLISHED) {
            throw new AppException(ErrorCode.QUIZ_NOT_PUBLISHED);
        }

        List<Question> questions = quiz.getQuestions();

        double earnedScore = 0.0;
        double totalPossiblePoints = questions.stream().mapToDouble(Question::getPoints).sum();

        Map<UUID, Set<UUID>> answerMap = request.answers().stream()
                .collect(Collectors.toMap(
                        QuestionAnswerRequest::questionId,
                        ans -> new HashSet<>(ans.selectedOptionIds())));

        QuizSubmission submission = new QuizSubmission();
        submission.setQuiz(quiz);
        submission.setUser(currentUser);
        submission.setTimeSpentSeconds(request.timeSpentSeconds());
        submission.setSubmittedAt(Instant.now());

        List<QuestionResultResponse> questionResults = new ArrayList<>();

        for (Question question : questions) {
            Set<UUID> selectedOptionIds = answerMap.getOrDefault(
                    question.getId(), Collections.emptySet());

            // 1. Chấm điểm câu hỏi thông qua hàm dùng chung
            QuestionResultResponse qResult = buildQuestionResult(question, selectedOptionIds);
            earnedScore += qResult.earnedPoints();

            questionResults.add(qResult);

            // 2. Lưu vết các lựa chọn của học viên vào bài nộp
            Map<UUID, QuestionOption> optionMap = question.getOptions().stream()
                    .collect(Collectors.toMap(QuestionOption::getId, Function.identity()));

            for (UUID optionId : selectedOptionIds) {
                QuestionOption selectedOption = optionMap.get(optionId);
                if (selectedOption != null) {
                    QuizSubmissionOption submissionOption = new QuizSubmissionOption();
                    submissionOption.setSelectedOption(selectedOption);
                    submissionOption.setIsCorrect(Boolean.TRUE.equals(selectedOption.getIsCorrect()));
                    submission.addSubmissionOption(submissionOption);
                }
            }
        }

        // Tính tổng điểm, tỷ lệ phần trăm và kết quả Đạt / Trượt
        earnedScore = roundToTwoDecimals(earnedScore);
        double percentage = totalPossiblePoints > 0 ? roundToTwoDecimals((earnedScore / totalPossiblePoints) * 100.0)
                : 0.0;
        boolean isPassed = percentage >= quiz.getPassingScore();

        submission.setScore(earnedScore);
        submission.setTotalPoints(totalPossiblePoints);
        submission.setIsPassed(isPassed);

        // Lưu bài nộp
        quizSubmissionRepository.save(submission);

        // Nối mắt xích: Tự động đánh dấu hoàn thành bài học nếu đạt điểm qua bài quiz
        if (isPassed) {
            try {
                enrollmentService.updateLessonProgress(
                        quiz.getLesson().getId(),
                        new UpdateProgressRequest(LessonProgressStatus.COMPLETED)
                );
            } catch (AppException e) {
                // Nếu người làm quiz là creator/admin chưa enroll khóa học, bỏ qua cập nhật tiến độ
                if (e.getErrorCode() != ErrorCode.NOT_ENROLLED) {
                    throw e;
                }
            }
        }

        // Trả về kết quả hoàn chỉnh
        return new QuizResultResponse(
                submission.getId(),
                quiz.getId(),
                earnedScore,
                totalPossiblePoints,
                percentage,
                isPassed,
                submission.getTimeSpentSeconds(),
                submission.getSubmittedAt(),
                questionResults);
    }

    @Override
    public List<QuizSubmissionSummaryResponse> getMySubmissions(UUID quizId) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        List<QuizSubmission> submissions = quizSubmissionRepository
                .findByQuizIdAndUserIdOrderBySubmittedAtDesc(quizId, currentUserId);

        return submissions.stream()
                .map(quizSubmissionMapper::toSummaryResponse)
                .toList();
    }

    @Override
    public List<QuizSubmissionSummaryResponse> getQuizSubmissionsForInstructor(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
                () -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        courseAccessValidator.validateCreatorOrAdminByLessonId(quiz.getLesson().getId());

        return quizSubmissionRepository.findByQuizIdOrderBySubmittedAtDesc(quizId).stream()
                .map(quizSubmissionMapper::toSummaryResponse)
                .toList();
    }

    @Override
    public QuizResultResponse getSubmissionDetail(UUID submissionId) {
        QuizSubmission submission = quizSubmissionRepository.findById(submissionId).orElseThrow(
                () -> new AppException(ErrorCode.SUBMISSION_NOT_FOUND));

        UUID currentUserId = SecurityUtils.getCurrentUserId();
        boolean isOwner = submission.getUser().getId().equals(currentUserId);
        boolean isInstructor = courseAccessValidator
                .isCreatorOrAdminByLessonId(submission.getQuiz().getLesson().getId());

        if (!isOwner && !isInstructor) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        Set<UUID> chosenOptionIds = quizSubmissionOptionRepository.findBySubmissionId(submissionId).stream()
                .map(so -> so.getSelectedOption().getId())
                .collect(Collectors.toSet());

        List<QuestionResultResponse> questionResults = submission.getQuiz().getQuestions().stream()
                .map(q -> buildQuestionResult(q, chosenOptionIds))
                .toList();

        double percentage = submission.getTotalPoints() != null && submission.getTotalPoints() > 0
                ? roundToTwoDecimals((submission.getScore() / submission.getTotalPoints()) * 100.0)
                : 0.0;

        return new QuizResultResponse(
                submission.getId(),
                submission.getQuiz().getId(),
                submission.getScore(),
                submission.getTotalPoints(),
                percentage,
                submission.getIsPassed(),
                submission.getTimeSpentSeconds(),
                submission.getSubmittedAt(),
                questionResults);
    }

    private QuestionResultResponse buildQuestionResult(Question question, Set<UUID> allSelectedIds) {
        Set<UUID> correctIds = question.getOptions().stream()
                .filter(QuestionOption::getIsCorrect)
                .map(QuestionOption::getId)
                .collect(Collectors.toSet());

        Set<UUID> selectedIds = question.getOptions().stream()
                .map(QuestionOption::getId)
                .filter(allSelectedIds::contains)
                .collect(Collectors.toSet());

        int totalCorrect = correctIds.size();
        int totalIncorrect = question.getOptions().size() - totalCorrect;

        long selectedCorrect = selectedIds.stream().filter(correctIds::contains).count();
        long selectedIncorrect = selectedIds.size() - selectedCorrect;

        double correctScore = totalCorrect > 0 ? ((double) selectedCorrect / totalCorrect * question.getPoints()) : 0.0;
        double incorrectScore = totalIncorrect > 0
                ? ((double) selectedIncorrect / totalIncorrect * question.getPoints())
                : 0.0;
        double earnedPoints = roundToTwoDecimals(Math.max(correctScore - incorrectScore, 0.0));

        return new QuestionResultResponse(
                question.getId(),
                question.getContent(),
                question.getExplanation(),
                question.getPoints(),
                earnedPoints,
                new ArrayList<>(selectedIds),
                new ArrayList<>(correctIds),
                selectedIds.equals(correctIds));
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
