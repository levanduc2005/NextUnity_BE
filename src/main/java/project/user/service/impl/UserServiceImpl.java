package project.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.dto.request.ChangePasswordRequest;
import project.user.dto.request.CheckPasswordRequest;
import project.user.dto.request.UpdateUserProfileRequest;
import project.user.dto.response.UserResponse;
import project.user.entity.User;
import project.user.entity.enums.SubscriptionTier;
import project.user.mapper.UserMapper;
import project.user.repository.UserRepository;
import project.user.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUserProfile() {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        User user = getUserById(currentUserId);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserProfileById(UUID id) {
        User user = getUserById(id);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUserProfile(UpdateUserProfileRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        User user = getUserById(currentUserId);
        userMapper.updateUserProfile(request, user);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public void checkCurrentPassword(CheckPasswordRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        User user = getUserById(currentUserId);
        if (user.getPasswordHash() == null || !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.PASSWORD_INCORRECT);
        }
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        User user = getUserById(currentUserId);
        if (user.getPasswordHash() == null || !passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new AppException(ErrorCode.PASSWORD_INCORRECT);
        }
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(String role, SubscriptionTier subscriptionTier, String search, Pageable pageable) {
        String cleanSearch = (search != null && !search.isBlank()) ? search.trim() : null;
        String cleanRole = (role != null && !role.isBlank()) ? role.trim() : null;
        return userRepository.searchUsers(cleanRole, subscriptionTier, cleanSearch, pageable)
                .map(userMapper::toResponse);
    }

    @Override
    @Transactional
    public void deleteUserByAdmin(UUID id) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId.equals(id)) {
            throw new AppException(ErrorCode.CANNOT_DELETE_SELF);
        }
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
