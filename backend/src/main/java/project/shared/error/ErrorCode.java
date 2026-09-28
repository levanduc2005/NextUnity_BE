package project.shared.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Authentication
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Không có quyền truy cập"),
    // OTP
    OTP_ALREADY_SENT(HttpStatus.BAD_REQUEST, "OTP đã được gửi, vui lòng thử lại sau"),
    OTP_EXPIRED(HttpStatus.BAD_REQUEST, "OTP expired"),
    INVALID_OTP(HttpStatus.BAD_REQUEST, "Invalid OTP"),
    // Login
    PASSWORD_INCORRECT(HttpStatus.BAD_REQUEST, "Password không chính xác"),
    // Refresh token
    REFRESH_TOKEN_INVALID(HttpStatus.BAD_REQUEST, "Refresh token không hợp lệ"),
    REFRESH_TOKEN_REVOKED(HttpStatus.BAD_REQUEST, "Refresh token đã bị thu hồi"),
    REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "Refresh token đã hết hạn"),
    // Role
    ROLE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Role đã tồn tại"),
    ROLE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found"),
    // User
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "Email đã tồn tại"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User không tồn tại"),
    // Mail
    CANNOT_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "Không thể gửi email"),
    // Community
    COMMUNITY_NAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Tên cộng đồng đã tồn tại"),
    NOT_COMMUNITY_CREATOR(HttpStatus.FORBIDDEN, "Bạn không phải là người tạo cộng đồng"),
    COMMUNITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Cộng đồng không tồn tại"),
    ALREADY_JOINED_COMMUNITY(HttpStatus.BAD_REQUEST, "Bạn đã tham gia cộng đồng này"),
    CANNOT_LEAVE_AS_CREATOR(HttpStatus.BAD_REQUEST, "Người tạo cộng đồng không thể rời khỏi cộng đồng"),
    NOT_A_MEMBER(HttpStatus.BAD_REQUEST, "Bạn không phải là thành viên của cộng đồng này"),
    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Bài viết không tồn tại"),
    NOT_POST_AUTHOR(HttpStatus.FORBIDDEN, "Bạn không phải là tác giả của bài viết này"),
    // Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Bình luận không tồn tại"),
    NOT_COMMENT_AUTHOR(HttpStatus.FORBIDDEN, "Bạn không phải là tác giả của bình luận này"),
    // Course
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "Khóa học không tồn tại"),
    NOT_COURSE_CREATOR(HttpStatus.FORBIDDEN, "Bạn không phải là người tạo khóa học này"),
    // Module & Lesson
    MODULE_NOT_FOUND(HttpStatus.NOT_FOUND, "Chương học không tồn tại"),
    LESSON_NOT_FOUND(HttpStatus.NOT_FOUND, "Bài học không tồn tại"),
    // Enrollment & Progress
    ALREADY_ENROLLED(HttpStatus.BAD_REQUEST, "Bạn đã đăng ký khóa học này rồi"),
    ENROLLMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Không tìm thấy thông tin đăng ký khóa học"),
    NOT_ENROLLED(HttpStatus.FORBIDDEN, "Bạn chưa đăng ký khóa học này"),
    // Flashcard
    FLASHCARD_NOT_FOUND(HttpStatus.NOT_FOUND, "Flashcard không tồn tại"),
    INVALID_FLASHCARD_REORDER(HttpStatus.BAD_REQUEST, "Danh sách sắp xếp flashcard không hợp lệ"),
    // Mindmap
    MINDMAP_NOT_FOUND(HttpStatus.NOT_FOUND, "Sơ đồ tư duy không tồn tại"),
    // Slide
    SLIDE_NOT_FOUND(HttpStatus.NOT_FOUND, "Slide hoặc tài liệu không tồn tại"),
    NOT_SLIDE_UPLOADER(HttpStatus.FORBIDDEN, "Bạn không phải là người tải lên tài liệu này"),
    INVALID_SLIDE_ACTION(HttpStatus.BAD_REQUEST, "Thao tác trên slide không hợp lệ"),
    // Quiz & Question
    QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "Bài trắc nghiệm không tồn tại"),
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Câu hỏi không tồn tại"),
    OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Phương án trả lời không tồn tại"),
    QUIZ_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Bài học này đã có bài trắc nghiệm"),
    INVALID_QUIZ_SUBMISSION(HttpStatus.BAD_REQUEST, "Dữ liệu nộp bài trắc nghiệm không hợp lệ"),
    INVALID_QUESTION_REORDER(HttpStatus.BAD_REQUEST, "Danh sách sắp xếp câu hỏi không hợp lệ"),
    INVALID_QUESTION_OPTION_COUNT(HttpStatus.BAD_REQUEST, "Số lượng phương án trả lời không hợp lệ"),
    INVALID_OPTION_REORDER(HttpStatus.BAD_REQUEST, "Danh sách sắp xếp lựa chọn không hợp lệ"),
    QUIZ_NOT_PUBLISHED(HttpStatus.BAD_REQUEST, "Bài trắc nghiệm chưa được xuất bản"),
    QUIZ_CANNOT_PUBLISH_EMPTY(HttpStatus.BAD_REQUEST, "Không thể xuất bản bài trắc nghiệm chưa có câu hỏi nào"),
    SUBMISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "Bài nộp trắc nghiệm không tồn tại");

    private final HttpStatus statusCode;
    private final String message;
}
