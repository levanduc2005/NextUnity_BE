package project.course.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.course.dto.course.request.CreateCourseRequest;
import project.course.dto.course.request.UpdateCourseRequest;
import project.course.dto.course.response.CourseDetailResponse;
import project.course.dto.course.response.CourseSummaryResponse;
import project.course.entity.enums.CourseDifficulty;
import project.course.service.CourseService;
import project.shared.response.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<CourseSummaryResponse> createCourse(
            @Valid @RequestBody CreateCourseRequest request
    ) {
        CourseSummaryResponse response = courseService.createCourse(request);
        return ApiResponse.success(response, "Tạo khóa học thành công");
    }

    @GetMapping
    public ApiResponse<Page<CourseSummaryResponse>> searchCourses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) CourseDifficulty difficulty,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<CourseSummaryResponse> response = courseService.searchCourses(category, difficulty, search, pageable);
        return ApiResponse.success(response, "Lấy danh sách khóa học thành công");
    }

    @GetMapping("/my-created")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<CourseSummaryResponse>> getMyCreatedCourses() {
        List<CourseSummaryResponse> response = courseService.getMyCreatedCourses();
        return ApiResponse.success(response, "Lấy danh sách khóa học của tôi thành công");
    }

    @GetMapping("/{id}")
    public ApiResponse<CourseDetailResponse> getCourseById(@PathVariable UUID id) {
        CourseDetailResponse response = courseService.getCourseById(id);
        return ApiResponse.success(response, "Lấy chi tiết khóa học thành công");
    }

    @PutMapping("/{id}")
    public ApiResponse<CourseSummaryResponse> updateCourse(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCourseRequest request
    ) {
        CourseSummaryResponse response = courseService.updateCourse(id, request);
        return ApiResponse.success(response, "Cập nhật khóa học thành công");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCourse(@PathVariable UUID id) {
        courseService.deleteCourse(id);
        return ApiResponse.success(null, "Xóa khóa học thành công");
    }
}
