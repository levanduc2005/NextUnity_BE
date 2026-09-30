package project.auth.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.auth.dto.request.CreateRoleRequest;
import project.auth.dto.request.UpdateRoleRequest;
import project.auth.dto.response.RoleResponse;
import project.auth.entity.Role;
import project.auth.mapper.RoleMapper;
import project.auth.repository.RoleRepository;
import project.auth.service.RoleService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Override
    public RoleResponse create(CreateRoleRequest request) {

        if (roleRepository.existsByName(request.name())) {
            throw new AppException(ErrorCode.ROLE_ALREADY_EXISTS);
        }

        Role role = Role.builder()
                .name(request.name().trim().toUpperCase())
                .description(request.description())
                .build();

        roleRepository.save(role);

        return roleMapper.toResponse(role);
    }

    @Override
    public RoleResponse update(UUID id, UpdateRoleRequest request) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new AppException(ErrorCode.ROLE_NOT_FOUND));

        roleMapper.updateRoleFromRequest(request, role);

        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getById(UUID id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new AppException(ErrorCode.ROLE_NOT_FOUND));

        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAll() {

        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new AppException(ErrorCode.ROLE_NOT_FOUND));

        roleRepository.delete(role);
    }
}
