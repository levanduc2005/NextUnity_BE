package project.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.dto.request.UpdateUserProfileRequest;
import project.user.dto.response.UserResponse;
import project.user.entity.User;
import project.user.mapper.UserMapper;
import project.user.repository.UserRepository;
import project.user.service.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
}
