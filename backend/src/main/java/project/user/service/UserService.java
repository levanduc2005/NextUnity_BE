package project.user.service;

import project.user.entity.User;

public interface UserService {
    boolean existsByEmail(String email);
    void saveUser(User user);
}
