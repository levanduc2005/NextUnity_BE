package project.course.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.course.dto.slide.request.RejectSlideRequest;
import project.course.dto.slide.request.UploadSlideRequest;
import project.course.dto.slide.response.SlideResponse;
import project.course.service.SlideUploadService;
import project.shared.response.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SlideUploadController {

    private final SlideUploadService slideUploadService;

    @GetMapping("/api/lessons/{lessonId}/slides")
    public ApiResponse<List<SlideResponse>> getSlidesByLessonId(@PathVariable UUID lessonId) {
        List<SlideResponse> response = slideUploadService.getSlidesByLessonId(lessonId);
        return ApiResponse.success(response, "Lấy danh sách slide thành công");
    }

    @PostMapping("/api/lessons/{lessonId}/slides")
    public ApiResponse<SlideResponse> uploadSlide(
            @PathVariable UUID lessonId,
            @Valid @RequestBody UploadSlideRequest request
    ) {
        SlideResponse response = slideUploadService.uploadSlide(lessonId, request);
        return ApiResponse.success(response, "Tải lên tài liệu thành công");
    }

    @PutMapping("/api/slides/{id}/contribute")
    public ApiResponse<SlideResponse> contributeSlide(@PathVariable UUID id) {
        SlideResponse response = slideUploadService.contributeSlide(id);
        return ApiResponse.success(response, "Gửi yêu cầu đóng góp tài liệu thành công");
    }

    @PutMapping("/api/slides/{id}/approve")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ApiResponse<SlideResponse> approveSlide(@PathVariable UUID id) {
        SlideResponse response = slideUploadService.approveSlide(id);
        return ApiResponse.success(response, "Phê duyệt tài liệu thành công");
    }

    @PutMapping("/api/slides/{id}/reject")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ApiResponse<SlideResponse> rejectSlide(
            @PathVariable UUID id,
            @Valid @RequestBody RejectSlideRequest request
    ) {
        SlideResponse response = slideUploadService.rejectSlide(id, request);
        return ApiResponse.success(response, "Từ chối tài liệu thành công");
    }

    @DeleteMapping("/api/slides/{id}")
    public ApiResponse<Void> deleteSlide(@PathVariable UUID id) {
        slideUploadService.deleteSlide(id);
        return ApiResponse.success(null, "Xóa tài liệu thành công");
    }
}
