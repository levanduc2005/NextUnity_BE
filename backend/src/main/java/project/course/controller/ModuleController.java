package project.course.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.course.dto.module.request.CreateModuleRequest;
import project.course.dto.module.request.UpdateModuleRequest;
import project.course.dto.module.response.ModuleResponse;
import project.course.service.ModuleService;
import project.shared.response.ApiResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping("/api/courses/{courseId}/modules")
    public ApiResponse<ModuleResponse> createModule(
            @PathVariable UUID courseId,
            @Valid @RequestBody CreateModuleRequest request
    ) {
        ModuleResponse response = moduleService.createModule(courseId, request);
        return ApiResponse.success(response, "Tạo chương học thành công");
    }

    @PutMapping("/api/modules/{id}")
    public ApiResponse<ModuleResponse> updateModule(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateModuleRequest request
    ) {
        ModuleResponse response = moduleService.updateModule(id, request);
        return ApiResponse.success(response, "Cập nhật chương học thành công");
    }

    @DeleteMapping("/api/modules/{id}")
    public ApiResponse<Void> deleteModule(@PathVariable UUID id) {
        moduleService.deleteModule(id);
        return ApiResponse.success(null, "Xóa chương học thành công");
    }
}
