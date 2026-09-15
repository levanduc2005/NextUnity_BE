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
import project.course.repository.EnrollmentRepository;
import project.course.repository.LessonRepository;
import project.course.repository.ModuleRepository;
import project.course.service.LessonService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;

    private final LessonMapper lessonMapper;

    @Override
    @Transactional
    public LessonDetailResponse createLesson(UUID moduleId, CreateLessonRequest request) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new AppException(ErrorCode.MODULE_NOT_FOUND));

        Course course = module.getCourse();
        checkCourseCreator(course);

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

        UUID currentUserId = SecurityUtils.getCurrentUserId();
        Course course = lesson.getCourse();

        boolean isCreator = course.getCreator().getId().equals(currentUserId);
        if(!isCreator){
            boolean isEnrolled = enrollmentRepository.existsByUserIdAndCourseId(currentUserId, course.getId());
            if(!isEnrolled){
                throw new AppException(ErrorCode.NOT_ENROLLED);
            }
        }

        return lessonMapper.toDetailResponse(lesson);
    }

    @Override
    public LessonDetailResponse updateLesson(UUID lessonId, UpdateLessonRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        Course course = lesson.getModule().getCourse();
        checkCourseCreator(course);

        lessonMapper.updateEntity(request, lesson);

        return lessonMapper.toDetailResponse(lessonRepository.save(lesson));
    }

    @Override
    @Transactional
    public void deleteLesson(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        Course course = lesson.getModule().getCourse();
        checkCourseCreator(course);

        course.setTotalLessons(Math.max(0, course.getTotalLessons() - 1));
        courseRepository.save(course);

        lessonRepository.delete(lesson);
    }

    private void checkCourseCreator(Course course) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        boolean isCreator = course.getCreator().getId().equals(currentUserId);
        boolean isAdmin = SecurityUtils.isCurrentUserAdmin();
        // Nếu không phải người tạo VÀ cũng không phải Admin thì chặn
        if (!isCreator && !isAdmin) {
            throw new AppException(ErrorCode.NOT_COURSE_CREATOR);
        }
    }
}
