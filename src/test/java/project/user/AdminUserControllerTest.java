package project.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.shared.response.ApiResponse;
import project.user.controller.AdminUserController;
import project.user.dto.response.UserResponse;
import project.user.entity.enums.SubscriptionTier;
import project.user.service.UserService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminUserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminUserController adminUserController;

    private UserResponse sampleUserResponse;
    private UUID sampleUserId;

    @BeforeEach
    void setUp() {
        sampleUserId = UUID.randomUUID();
        sampleUserResponse = new UserResponse(
                sampleUserId.toString(),
                "Nguyễn Văn A",
                "a@gmail.com",
                "FPT University",
                "SE123456",
                "https://avatar.url",
                100,
                5,
                null,
                "Nam",
                null,
                18,
                "Kỹ thuật phần mềm",
                "Xin chào",
                null,
                "STUDENT",
                SubscriptionTier.FREE,
                null,
                false
        );
    }

    @Test
    void getAllUsers_shouldReturnPagedUsers() {
        Page<UserResponse> page = new PageImpl<>(List.of(sampleUserResponse));
        when(userService.getAllUsers(any(), any(), any(), any(Pageable.class))).thenReturn(page);

        ApiResponse<Page<UserResponse>> response = adminUserController.getAllUsers(
                "STUDENT", SubscriptionTier.FREE, "Nguyen", 0, 10, "createdAt", "desc"
        );

        assertNotNull(response);
        assertEquals(1, response.getData().getContent().size());
        assertEquals("Nguyễn Văn A", response.getData().getContent().get(0).fullName());
        verify(userService, times(1)).getAllUsers(eq("STUDENT"), eq(SubscriptionTier.FREE), eq("Nguyen"), any(Pageable.class));
    }

    @Test
    void getUserById_shouldReturnUserDetails() {
        when(userService.getUserProfileById(sampleUserId)).thenReturn(sampleUserResponse);

        ApiResponse<UserResponse> response = adminUserController.getUserById(sampleUserId);

        assertNotNull(response);
        assertEquals(sampleUserResponse.id(), response.getData().id());
        assertEquals(sampleUserResponse.email(), response.getData().email());
        verify(userService, times(1)).getUserProfileById(sampleUserId);
    }

    @Test
    void deleteUser_shouldInvokeService() {
        doNothing().when(userService).deleteUserByAdmin(sampleUserId);

        ApiResponse<Void> response = adminUserController.deleteUser(sampleUserId);

        assertNotNull(response);
        assertEquals("Xóa người dùng thành công", response.getMessage());
        verify(userService, times(1)).deleteUserByAdmin(sampleUserId);
    }
}
