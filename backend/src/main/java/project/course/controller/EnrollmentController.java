package project.course.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.course.dto.enrollment.response.EnrollmentResponse;
import project.course.dto.progress.request.UpdateProgressRequest;
import project.course.dto.progress.response.LessonProgressResponse;
import project.course.service.EnrollmentService;
import project.shared.response.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    // Đăng ký tham gia khóa học
    @PostMapping("/api/courses/{courseId}/enroll")
    public ApiResponse<EnrollmentResponse> enrollCourse(@PathVariable UUID courseId) {
        EnrollmentResponse response = enrollmentService.enrollCourse(courseId);
        return ApiResponse.success(response, "Đăng ký khóa học thành công");
    }

    // Lấy danh sách các khóa học đã đăng ký của tôi kèm % tiến độ
    @GetMapping("/api/enrollments/my-courses")
    public ApiResponse<List<EnrollmentResponse>> getMyEnrollments() {
        List<EnrollmentResponse> response = enrollmentService.getMyEnrollments();
        return ApiResponse.success(response, "Lấy danh sách khóa học đã đăng ký thành công");
    }

    // Lấy thông tin đăng ký của 1 khóa học cụ thể
    @GetMapping("/api/enrollments/course/{courseId}")
    public ApiResponse<EnrollmentResponse> getMyEnrollmentByCourseId(@PathVariable UUID courseId) {
        EnrollmentResponse response = enrollmentService.getMyEnrollmentByCourseId(courseId);
        return ApiResponse.success(response, "Lấy thông tin tiến độ khóa học thành công");
    }

    // Cập nhật tiến độ bài học (ví dụ: đánh dấu hoàn thành COMPLETED)
    @PostMapping("/api/lessons/{lessonId}/progress")
    public ApiResponse<LessonProgressResponse> updateLessonProgress(
            @PathVariable UUID lessonId,
            @Valid @RequestBody UpdateProgressRequest request
    ) {
        LessonProgressResponse response = enrollmentService.updateLessonProgress(lessonId, request);
        return ApiResponse.success(response, "Cập nhật tiến độ bài học thành công");
    }

    // Lấy toàn bộ tiến độ các bài học trong một khóa học của user
    @GetMapping("/api/courses/{courseId}/progress")
    public ApiResponse<List<LessonProgressResponse>> getMyProgressByCourseId(@PathVariable UUID courseId) {
        List<LessonProgressResponse> response = enrollmentService.getMyProgressByCourseId(courseId);
        return ApiResponse.success(response, "Lấy danh sách tiến độ bài học thành công");
    }
}
