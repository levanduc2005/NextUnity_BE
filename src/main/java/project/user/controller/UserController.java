package project.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.shared.response.ApiResponse;
import project.user.dto.request.ChangePasswordRequest;
import project.user.dto.request.CheckPasswordRequest;
import project.user.dto.request.UpdateUserProfileRequest;
import project.user.dto.response.UserResponse;
import project.user.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser() {
        UserResponse response = userService.getCurrentUserProfile();
        return ApiResponse.success(response, "Lấy thông tin người dùng hiện tại thành công");
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable UUID id) {
        UserResponse response = userService.getUserProfileById(id);
        return ApiResponse.success(response, "Lấy thông tin người dùng thành công");
    }

    @PutMapping("/me")
    public ApiResponse<UserResponse> updateProfile(@Valid @RequestBody UpdateUserProfileRequest request) {
        UserResponse response = userService.updateUserProfile(request);
        return ApiResponse.success(response, "Cập nhật thông tin người dùng thành công");
    }

    @PostMapping("/check-password")
    public ApiResponse<Void> checkPassword(@Valid @RequestBody CheckPasswordRequest request) {
        userService.checkCurrentPassword(request);
        return ApiResponse.success(null, "Mật khẩu hiện tại chính xác");
    }

    @PutMapping("/change-password")
    public ApiResponse<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ApiResponse.success(null, "Đổi mật khẩu thành công");
    }
}
