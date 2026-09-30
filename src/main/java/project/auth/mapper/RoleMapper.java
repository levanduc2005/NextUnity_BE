package project.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import project.auth.dto.request.UpdateRoleRequest;
import project.auth.dto.response.RoleResponse;
import project.auth.entity.Role;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper {
    RoleResponse toResponse(Role role);

    void updateRoleFromRequest(UpdateRoleRequest request, @MappingTarget Role role);
}
