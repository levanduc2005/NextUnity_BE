package project.identity.service;

import project.identity.dto.request.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
}
