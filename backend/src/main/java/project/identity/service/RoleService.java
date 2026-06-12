package project.identity.service;

import project.identity.dto.request.CreateRoleRequest;
import project.identity.dto.request.UpdateRoleRequest;
import project.identity.dto.response.RoleResponse;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    RoleResponse create(CreateRoleRequest request);

    RoleResponse update(UUID id, UpdateRoleRequest request);

    RoleResponse getById(UUID id);

    List<RoleResponse> getAll();

    void delete(UUID id);
}
