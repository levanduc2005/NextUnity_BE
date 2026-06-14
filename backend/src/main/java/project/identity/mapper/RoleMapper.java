package project.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import project.identity.dto.request.UpdateRoleRequest;
import project.identity.dto.response.RoleResponse;
import project.identity.entity.Role;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper {
    RoleResponse toResponse(Role role);

    void updateRoleFromRequest(UpdateRoleRequest request, @MappingTarget Role role);
}
