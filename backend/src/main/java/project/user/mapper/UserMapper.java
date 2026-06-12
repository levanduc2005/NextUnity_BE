package project.user.mapper;

import org.mapstruct.Mapper;
import project.user.dto.response.UserResponse;
import project.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
}
