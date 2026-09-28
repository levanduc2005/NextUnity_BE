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

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CourseAccessValidator {

    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;

    /**
     * 1. Quyền quản trị: Chỉ Creator của Course hoặc ADMIN mới có quyền can thiệp
     * (Thêm/Sửa/Xóa).
     */
    public void validateCreatorOrAdmin(Course course) {
        if (!isCreatorOrAdmin(course)) {
            throw new AppException(ErrorCode.NOT_COURSE_CREATOR);
        }
    }

    public void validateCreatorOrAdminByLessonId(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));
        validateCreatorOrAdmin(lesson.getCourse());
    }

    public void validateAccessByLessonId(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));
        validateAccess(lesson.getCourse());
    }

    /**
     * 2. Quyền xem nội dung: Creator, ADMIN hoặc Học viên ĐÃ ĐĂNG KÝ khóa học
     * (Enrolled).
     */
    public void validateAccess(Course course) {
        if (!isCreatorOrAdmin(course)) {
            UUID currentUserId = SecurityUtils.getCurrentUserId();
            boolean isEnrolled = enrollmentRepository.existsByUserIdAndCourseId(currentUserId, course.getId());
            if (!isEnrolled) {
                throw new AppException(ErrorCode.NOT_ENROLLED);
            }
        }
    }

    /**
     * 3. Hàm kiểm tra điều kiện (trả về boolean): Dùng khi cần rẽ nhánh logic (như
     * phân loại Slide Official vs Private).
     */
    public boolean isCreatorOrAdmin(Course course) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        boolean isCreator = course.getCreator() != null && course.getCreator().getId().equals(currentUserId);
        boolean isAdmin = SecurityUtils.isCurrentUserAdmin();
        return isCreator || isAdmin;
    }

    public boolean isCreatorOrAdminByLessonId(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.LESSON_NOT_FOUND));
        return isCreatorOrAdmin(lesson.getCourse());
    }
}
