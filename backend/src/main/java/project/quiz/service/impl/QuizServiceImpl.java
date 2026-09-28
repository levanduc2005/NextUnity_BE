package project.quiz.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.course.entity.Lesson;
import project.course.repository.LessonRepository;
import project.course.validator.CourseAccessValidator;
import project.quiz.dto.quiz.request.CreateQuizRequest;
import project.quiz.dto.quiz.request.UpdateQuizRequest;
import project.quiz.dto.quiz.response.QuizAdminResponse;
import project.quiz.dto.quiz.response.TakeQuizResponse;
import project.quiz.entity.Quiz;
import project.quiz.entity.enums.QuizStatus;
import project.quiz.mapper.QuizMapper;
import project.quiz.repository.QuizRepository;
import project.quiz.service.QuizService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final LessonRepository lessonRepository;
    private final CourseAccessValidator courseAccessValidator;
    private final QuizMapper quizMapper;

    @Override
    public QuizAdminResponse getQuizAdmin(UUID lessonId) {
        courseAccessValidator.validateCreatorOrAdminByLessonId(lessonId);

        Quiz quiz = quizRepository.findByLessonId(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        return quizMapper.toAdminResponse(quiz);
    }

    @Override
    public TakeQuizResponse getQuizForStudent(UUID lessonId) {
        courseAccessValidator.validateAccessByLessonId(lessonId);

        Quiz quiz = quizRepository.findByLessonId(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        if (quiz.getStatus() != QuizStatus.PUBLISHED) {
            throw new AppException(ErrorCode.QUIZ_NOT_PUBLISHED);
        }

        return quizMapper.toTakeQuizResponse(quiz);
    }

    @Override
    @Transactional
    public QuizAdminResponse createQuiz(UUID lessonId, CreateQuizRequest request) {
        courseAccessValidator.validateCreatorOrAdminByLessonId(lessonId);

        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        if (quizRepository.existsByLessonId(lessonId)) {
            throw new AppException(ErrorCode.QUIZ_ALREADY_EXISTS);
        }

        Quiz quiz = quizMapper.toEntity(request);
        quiz.setLesson(lesson);
        quiz.setStatus(QuizStatus.DRAFT);

        return quizMapper.toAdminResponse(quizRepository.save(quiz));
    }

    @Override
    @Transactional
    public QuizAdminResponse updateQuiz(UUID quizId, UpdateQuizRequest request) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
                () -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        courseAccessValidator.validateCreatorOrAdminByLessonId(quiz.getLesson().getId());

        quizMapper.updateEntity(request, quiz);

        return quizMapper.toAdminResponse(quizRepository.save(quiz));
    }

    @Override
    @Transactional
    public QuizAdminResponse togglePublish(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
                () -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        courseAccessValidator.validateCreatorOrAdminByLessonId(quiz.getLesson().getId());

        if (quiz.getStatus() == QuizStatus.DRAFT) {
            // Guard: Đề thi phải có ít nhất 1 câu hỏi mới được phép xuất bản
            if (quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
                throw new AppException(ErrorCode.QUIZ_CANNOT_PUBLISH_EMPTY);
            }
            quiz.setStatus(QuizStatus.PUBLISHED);
        } else {
            quiz.setStatus(QuizStatus.DRAFT);
        }

        return quizMapper.toAdminResponse(quizRepository.save(quiz));
    }

    @Override
    @Transactional
    public void deleteQuiz(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
                () -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        courseAccessValidator.validateCreatorOrAdminByLessonId(quiz.getLesson().getId());

        quizRepository.delete(quiz);
    }
}
