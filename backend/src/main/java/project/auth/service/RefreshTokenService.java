package project.auth.service;

import project.auth.entity.RefreshToken;
import project.user.entity.User;

public interface RefreshTokenService {

    RefreshToken create(User user);

    RefreshToken verify(String token);

    void revoke(String token);
}
