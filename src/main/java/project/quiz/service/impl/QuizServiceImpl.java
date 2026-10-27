package project.quiz.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.course.entity.Course;
import project.course.entity.Lesson;
import project.course.entity.Module;
import project.course.entity.enums.LessonType;
import project.course.repository.CourseRepository;
import project.course.repository.LessonRepository;
import project.course.repository.ModuleRepository;
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
    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final CourseAccessValidator courseAccessValidator;
    private final QuizMapper quizMapper;

    @Override
    public QuizAdminResponse getQuizAdmin(UUID lessonId) {
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
    public TakeQuizResponse getQuizByIdForStudent(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
                () -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        courseAccessValidator.validateAccessByLessonId(quiz.getLesson().getId());

        if (quiz.getStatus() != QuizStatus.PUBLISHED) {
            throw new AppException(ErrorCode.QUIZ_NOT_PUBLISHED);
        }

        return quizMapper.toTakeQuizResponse(quiz);
    }

    @Override
    @Transactional
    public QuizAdminResponse createQuiz(UUID moduleId, CreateQuizRequest request) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new AppException(ErrorCode.MODULE_NOT_FOUND));

        int orderIndex = lessonRepository.countByModuleId(moduleId) + 1;

        // 1. Tạo bản ghi Lesson đại diện loại QUIZ trong Module
        Lesson lesson = new Lesson();
        lesson.setModule(module);
        lesson.setTitle(request.title());
        lesson.setContent(request.description());
        lesson.setType(LessonType.QUIZ);
        lesson.setOrderIndex(orderIndex);
        lesson.setEstimatedMinutes(request.durationMinutes());
        lesson = lessonRepository.save(lesson);

        // 2. Tạo Quiz liên kết 1-1 với Lesson vừa tạo
        Quiz quiz = quizMapper.toEntity(request);
        quiz.setLesson(lesson);
        quiz.setStatus(QuizStatus.DRAFT);
        quiz = quizRepository.save(quiz);

        lesson.setQuiz(quiz);

        // 3. Cập nhật tổng số bài học của khóa học
        Course course = module.getCourse();
        if (course != null && course.getTotalLessons() != null) {
            course.setTotalLessons(course.getTotalLessons() + 1);
            courseRepository.save(course);
        }

        return quizMapper.toAdminResponse(quiz);
    }

    @Override
    @Transactional
    public QuizAdminResponse updateQuiz(UUID quizId, UpdateQuizRequest request) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
                () -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        quizMapper.updateEntity(request, quiz);

        return quizMapper.toAdminResponse(quizRepository.save(quiz));
    }

    @Override
    @Transactional
    public QuizAdminResponse togglePublish(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(
                () -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

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

        Lesson lesson = quiz.getLesson();
        if (lesson != null) {
            Course course = lesson.getCourse();
            if (course != null && course.getTotalLessons() != null) {
                course.setTotalLessons(Math.max(0, course.getTotalLessons() - 1));
                courseRepository.save(course);
            }
            lessonRepository.delete(lesson);
        } else {
            quizRepository.delete(quiz);
        }
    }
}
