package project.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.user.entity.User;
import project.user.repository.UserRepository;
import project.user.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
