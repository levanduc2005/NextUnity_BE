package project.course.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.course.dto.mindmap.request.SaveMindmapRequest;
import project.course.dto.mindmap.response.MindmapResponse;
import project.course.service.MindmapService;
import project.shared.response.ApiResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons/{lessonId}/mindmap")
public class MindmapController {

    private final MindmapService mindmapService;

    @GetMapping
    public ApiResponse<MindmapResponse> getMindmapByLessonId(@PathVariable UUID lessonId) {
        MindmapResponse response = mindmapService.getMindmapByLessonId(lessonId);
        return ApiResponse.success(response, "Lấy sơ đồ tư duy thành công");
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ApiResponse<MindmapResponse> saveMindmap(
            @PathVariable UUID lessonId,
            @Valid @RequestBody SaveMindmapRequest request
    ) {
        MindmapResponse response = mindmapService.saveMindmap(lessonId, request);
        return ApiResponse.success(response, "Lưu sơ đồ tư duy thành công");
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ApiResponse<Void> deleteMindmap(@PathVariable UUID lessonId) {
        mindmapService.deleteMindmap(lessonId);
        return ApiResponse.success(null, "Xóa sơ đồ tư duy thành công");
    }
}
