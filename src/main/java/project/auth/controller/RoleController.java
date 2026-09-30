package project.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.auth.dto.request.CreateRoleRequest;
import project.auth.dto.request.UpdateRoleRequest;
import project.auth.dto.response.RoleResponse;
import project.auth.service.RoleService;
import project.shared.response.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(
            @Valid @RequestBody CreateRoleRequest request
    ) {
        RoleResponse createdRole = roleService.create(request);
        return ResponseEntity
                .status(201)
                .body(ApiResponse.success(createdRole, "Role created successfully"));
    }

    @GetMapping("/{id}")
    public ApiResponse<RoleResponse> getRoleById(
            @PathVariable UUID id
    ) {
        RoleResponse role = roleService.getById(id);
        return ApiResponse.success(role, "Role retrieved successfully");
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = roleService.getAll();
        return ApiResponse.success(roles, "Roles retrieved successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<RoleResponse> updateRole(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateRoleRequest request
    ) {
        RoleResponse updatedRole = roleService.update(id, request);
        return ApiResponse.success(updatedRole, "Role updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRole(
            @PathVariable UUID id
    ) {
        roleService.delete(id);

        return ApiResponse.success(null, "Role deleted successfully");
    }
}
