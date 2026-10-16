package project.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.user.dto.request.ChangePasswordRequest;
import project.user.dto.request.CheckPasswordRequest;
import project.user.dto.request.UpdateUserProfileRequest;
import project.user.dto.response.UserResponse;
import project.user.entity.User;
import project.user.entity.enums.SubscriptionTier;

import java.util.UUID;

public interface UserService {
    boolean existsByEmail(String email);

    boolean existsById(UUID id);

    void saveUser(User user);

    User getUserByEmail(String email);

    User getUserById(UUID id);

    UserResponse getCurrentUserProfile();

    UserResponse getUserProfileById(UUID id);

    UserResponse updateUserProfile(UpdateUserProfileRequest request);

    void checkCurrentPassword(CheckPasswordRequest request);

    void changePassword(ChangePasswordRequest request);

    Page<UserResponse> getAllUsers(String role, SubscriptionTier subscriptionTier, String search, Pageable pageable);

    void deleteUserByAdmin(UUID id);
}
