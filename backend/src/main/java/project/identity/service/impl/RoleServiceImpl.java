package project.identity.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.identity.dto.request.CreateRoleRequest;
import project.identity.dto.request.UpdateRoleRequest;
import project.identity.dto.response.RoleResponse;
import project.identity.entity.Role;
import project.identity.mapper.RoleMapper;
import project.identity.repository.RoleRepository;
import project.identity.service.RoleService;
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
