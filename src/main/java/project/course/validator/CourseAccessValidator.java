package project.course.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.course.entity.Course;
import project.course.entity.Lesson;
import project.course.repository.EnrollmentRepository;
import project.course.repository.LessonRepository;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.entity.User;
import project.user.service.UserService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CourseAccessValidator {

    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;

    private final UserService userService;

    /**
     * 1. Quyền xem nội dung: ADMIN (toàn quyền xem mọi bài học) hoặc Học viên ĐÃ ĐĂNG KÝ (Enrolled).
     */
    public void validateAccess(Course course) {
        if (!SecurityUtils.isCurrentUserAdmin()) {
            UUID currentUserId = SecurityUtils.getCurrentUserId();
            User currentUser = userService.getUserById(currentUserId);

            // 1. Khóa học Premium yêu cầu tài khoản PLUS còn hiệu lực
            if (Boolean.TRUE.equals(course.getIsPremium()) && !currentUser.isPlus()) {
                throw new AppException(ErrorCode.PREMIUM_REQUIRED);
            }

            // 2. Kiểm tra trạng thái đã đăng ký (Enrolled)
            boolean isEnrolled = enrollmentRepository.existsByUserIdAndCourseId(currentUserId, course.getId());
            if (!isEnrolled) {
                throw new AppException(ErrorCode.NOT_ENROLLED);
            }
        }
    }

    public void validateAccessByLessonId(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));
        validateAccess(lesson.getCourse());
    }
}
