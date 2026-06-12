package project.identity.mapper;

import org.mapstruct.Mapper;
import project.identity.dto.response.RoleResponse;
import project.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toResponse(Role role);
}
