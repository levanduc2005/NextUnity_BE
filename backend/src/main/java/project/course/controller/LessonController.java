package project.course.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.course.dto.lesson.request.CreateLessonRequest;
import project.course.dto.lesson.request.UpdateLessonRequest;
import project.course.dto.lesson.response.LessonDetailResponse;
import project.course.service.LessonService;
import project.shared.response.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/api/modules/{moduleId}/lessons")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<LessonDetailResponse> createLesson(
            @PathVariable UUID moduleId,
            @Valid @RequestBody CreateLessonRequest request
    ) {
        LessonDetailResponse response = lessonService.createLesson(moduleId, request);
        return ApiResponse.success(response, "Tạo bài học thành công");
    }

    @GetMapping("/api/lessons/{id}")
    public ApiResponse<LessonDetailResponse> getLessonById(@PathVariable UUID id) {
        LessonDetailResponse response = lessonService.getLessonById(id);
        return ApiResponse.success(response, "Lấy chi tiết bài học thành công");
    }

    @PutMapping("/api/lessons/{id}")
    public ApiResponse<LessonDetailResponse> updateLesson(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateLessonRequest request
    ) {
        LessonDetailResponse response = lessonService.updateLesson(id, request);
        return ApiResponse.success(response, "Cập nhật bài học thành công");
    }

    @DeleteMapping("/api/lessons/{id}")
    public ApiResponse<Void> deleteLesson(@PathVariable UUID id) {
        lessonService.deleteLesson(id);
        return ApiResponse.success(null, "Xóa bài học thành công");
    }
}
