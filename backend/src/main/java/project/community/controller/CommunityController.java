package project.community.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.community.dto.community.request.CreateCommunityRequest;
import project.community.dto.community.request.UpdateCommunityRequest;
import project.community.dto.community.response.CommunityResponse;
import project.community.service.CommunityService;
import project.shared.response.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    // Tạo 1 cộng đồng mới
    @PostMapping
    public ApiResponse<CommunityResponse> createCommunity(
            @Valid @RequestBody CreateCommunityRequest request
    ) {
        CommunityResponse response = communityService.createCommunity(request);
        return ApiResponse.success(response, "Tạo cộng đồng thành công");
    }

    // Lấy thông tin cộng đồng theo ID
    @GetMapping("/{id}")
    public ApiResponse<CommunityResponse> getCommunityById(@PathVariable UUID id) {
        CommunityResponse response = communityService.getCommunityById(id);
        return ApiResponse.success(response, "Lấy thông tin cộng đồng thành công");
    }

    // Lấy danh sách tất cả cộng đồng
    @GetMapping
    public ApiResponse<List<CommunityResponse>> getAllCommunities() {
        List<CommunityResponse> response = communityService.getAllCommunities();
        return ApiResponse.success(response, "Lấy danh sách cộng đồng thành công");
    }

    @PutMapping("/{id}")
    public ApiResponse<CommunityResponse> updateCommunity(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCommunityRequest request
    ) {
        CommunityResponse response = communityService.updateCommunity(id, request);
        return ApiResponse.success(response, "Cập nhật mô tả cộng đồng thành công");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCommunity(
            @PathVariable UUID id
    ) {
        communityService.deleteCommunity(id);
        return ApiResponse.success(null, "Xóa cộng đồng thành công");
    }
}
