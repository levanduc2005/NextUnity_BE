package project.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.course.dto.enrollment.response.EnrollmentResponse;
import project.course.dto.progress.request.UpdateProgressRequest;
import project.course.dto.progress.response.LessonProgressResponse;
import project.course.entity.Course;
import project.course.entity.Enrollment;
import project.course.entity.Lesson;
import project.course.entity.LessonProgress;
import project.course.entity.enums.EnrollmentStatus;
import project.course.entity.enums.LessonProgressStatus;
import project.course.mapper.EnrollmentMapper;
import project.course.mapper.LessonProgressMapper;
import project.course.repository.CourseRepository;
import project.course.repository.EnrollmentRepository;
import project.course.repository.LessonProgressRepository;
import project.course.repository.LessonRepository;
import project.course.service.EnrollmentService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.entity.User;
import project.user.service.UserService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final UserService userService;

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final LessonProgressRepository lessonProgressRepository;

    private final EnrollmentMapper enrollmentMapper;
    private final LessonProgressMapper lessonProgressMapper;

    @Override
    public EnrollmentResponse enrollCourse(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        UUID currentUserId = SecurityUtils.getCurrentUserId();
        User currentUser = userService.getUserById(currentUserId);
        if(enrollmentRepository.existsByUserIdAndCourseId(currentUserId, courseId)) {
            throw new AppException(ErrorCode.ALREADY_ENROLLED);
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setUser(currentUser);
        enrollment.setProgressPercent(0);
        enrollment.setStatus(EnrollmentStatus.ENROLLED);

        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }

    @Override
    public List<EnrollmentResponse> getMyEnrollments() {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        return enrollmentRepository.findByUserIdOrderByCreatedAtDesc(currentUserId)
                .stream()
                .map(enrollmentMapper::toResponse)
                .toList();
    }

    @Override
    public EnrollmentResponse getMyEnrollmentByCourseId(UUID courseId) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        return enrollmentRepository.findByUserIdAndCourseId(currentUserId, courseId)
                .map(enrollmentMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.ENROLLMENT_NOT_FOUND));
    }

    @Override
    public LessonProgressResponse updateLessonProgress(UUID lessonId, UpdateProgressRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));
        Course course = lesson.getCourse();
        if(course == null) {
            throw new AppException(ErrorCode.COURSE_NOT_FOUND);
        }

        UUID userId = SecurityUtils.getCurrentUserId();
        Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(userId, course.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_ENROLLED));

        LessonProgress lessonProgress = lessonProgressRepository.findByUserIdAndLessonId(userId, lessonId)
                .orElseGet(() -> {
                    LessonProgress newProgress = new LessonProgress();
                    newProgress.setUser(enrollment.getUser());
                    newProgress.setLesson(lesson);
                    return newProgress;
                });

        lessonProgress.setStatus(request.status());
        if(request.status().equals(LessonProgressStatus.COMPLETED)) {
            lessonProgress.setCompletedAt(Instant.now());
        }else{
            lessonProgress.setCompletedAt(null);
        }

        lessonProgress = lessonProgressRepository.save(lessonProgress);

        int totalLessons = course.getTotalLessons();
        int completedLessons = lessonProgressRepository.countByUserIdAndLesson_Module_Course_IdAndStatus(userId, course.getId(), LessonProgressStatus.COMPLETED);
        int progressPercent = totalLessons > 0 ? (completedLessons * 100) / totalLessons : 0;
        enrollment.setProgressPercent(progressPercent);

        if(progressPercent == 100){
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
            enrollment.setCompletedAt(Instant.now());
        } else if (progressPercent > 0) {
            enrollment.setStatus(EnrollmentStatus.IN_PROGRESS);
        } else  {
            enrollment.setStatus(EnrollmentStatus.ENROLLED);
        }

        enrollmentRepository.save(enrollment);

        return lessonProgressMapper.toResponse(lessonProgress, progressPercent);
    }

    @Override
    public List<LessonProgressResponse> getMyProgressByCourseId(UUID courseId) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        if(!courseRepository.existsById(courseId)) {
            throw new AppException(ErrorCode.COURSE_NOT_FOUND);
        }

        return lessonProgressRepository.findByUserIdAndLesson_Module_Course_Id(currentUserId, courseId)
                .stream()
                .map(lessonProgressMapper::toResponse)
                .toList();
    }
}
