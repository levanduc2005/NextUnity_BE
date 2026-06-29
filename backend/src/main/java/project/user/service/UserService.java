package project.user.service;

import project.user.entity.User;

import java.util.UUID;

public interface UserService {
    boolean existsByEmail(String email);
    boolean existsById(UUID id);
    void saveUser(User user);
    User getUserByEmail(String email);
    User getUserById(UUID id);
}
