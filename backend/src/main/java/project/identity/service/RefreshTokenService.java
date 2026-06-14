package project.identity.service;

import project.identity.entity.RefreshToken;
import project.user.entity.User;

public interface RefreshTokenService {

    RefreshToken create(User user);

    RefreshToken verify(String token);

    void revoke(String token);
}
