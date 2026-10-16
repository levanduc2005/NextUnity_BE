package project.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.course.dto.lesson.request.CreateLessonRequest;
import project.course.dto.lesson.request.UpdateLessonRequest;
import project.course.dto.lesson.response.LessonDetailResponse;
import project.course.entity.Course;
import project.course.entity.Lesson;
import project.course.entity.Module;
import project.course.mapper.LessonMapper;
import project.course.entity.enums.LessonProgressStatus;
import project.course.repository.CourseRepository;
import project.course.repository.LessonProgressRepository;
import project.course.repository.LessonRepository;
import project.course.repository.ModuleRepository;
import project.course.service.LessonService;
import project.course.validator.CourseAccessValidator;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonServiceImpl implements LessonService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;
    private final LessonProgressRepository lessonProgressRepository;

    private final LessonMapper lessonMapper;

    private final CourseAccessValidator courseAccessValidator;

    @Override
    @Transactional
    public LessonDetailResponse createLesson(UUID moduleId, CreateLessonRequest request) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new AppException(ErrorCode.MODULE_NOT_FOUND));

        Lesson lesson = lessonMapper.toEntity(request);
        lesson.setModule(module);
        lesson = lessonRepository.save(lesson);

        Course course = module.getCourse();
        if (course != null) {
            int currentTotal = course.getTotalLessons() != null ? course.getTotalLessons() : 0;
            course.setTotalLessons(currentTotal + 1);
            courseRepository.save(course);
        }

        return lessonMapper.toDetailResponse(lesson);

    }

    @Override
    public LessonDetailResponse getLessonById(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        courseAccessValidator.validateAccess(lesson.getCourse());

        boolean isCompleted = SecurityUtils.getOptionalCurrentUserId()
                .flatMap(userId -> lessonProgressRepository.findByUserIdAndLessonId(userId, lessonId))
                .map(lp -> lp.getStatus() == LessonProgressStatus.COMPLETED)
                .orElse(false);

        return lessonMapper.toDetailResponse(lesson, isCompleted);
    }

    @Override
    @Transactional
    public LessonDetailResponse updateLesson(UUID lessonId, UpdateLessonRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        lessonMapper.updateEntity(request, lesson);

        return lessonMapper.toDetailResponse(lessonRepository.save(lesson));
    }

    @Override
    @Transactional
    public void deleteLesson(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        Course course = lesson.getCourse();
        if (course != null) {
            int currentTotal = course.getTotalLessons() != null ? course.getTotalLessons() : 0;
            course.setTotalLessons(Math.max(0, currentTotal - 1));
            courseRepository.save(course);
        }

        lessonRepository.delete(lesson);
    }
}
