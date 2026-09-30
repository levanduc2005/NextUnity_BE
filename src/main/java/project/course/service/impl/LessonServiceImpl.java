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
import project.course.repository.CourseRepository;
import project.course.repository.LessonRepository;
import project.course.repository.ModuleRepository;
import project.course.service.LessonService;
import project.course.validator.CourseAccessValidator;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    private final LessonMapper lessonMapper;

    private final CourseAccessValidator courseAccessValidator;

    @Override
    @Transactional
    public LessonDetailResponse createLesson(UUID moduleId, CreateLessonRequest request) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new AppException(ErrorCode.MODULE_NOT_FOUND));

        Course course = module.getCourse();

        Lesson lesson = lessonMapper.toEntity(request);
        lesson.setModule(module);
        lesson = lessonRepository.save(lesson);

        course.setTotalLessons(course.getTotalLessons() + 1);
        courseRepository.save(course);

        return lessonMapper.toDetailResponse(lesson);

    }

    @Override
    public LessonDetailResponse getLessonById(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        courseAccessValidator.validateAccess(lesson.getCourse());

        return lessonMapper.toDetailResponse(lesson);
    }

    @Override
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

        Course course = lesson.getModule().getCourse();

        course.setTotalLessons(Math.max(0, course.getTotalLessons() - 1));
        courseRepository.save(course);

        lessonRepository.delete(lesson);
    }
}
