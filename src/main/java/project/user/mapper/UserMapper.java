package project.user.mapper;

import org.mapstruct.*;
import project.user.dto.request.UpdateUserProfileRequest;
import project.user.dto.response.UserResponse;
import project.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "isPlus", expression = "java(user.isPlus())")
    @Mapping(target = "role", source = "role.name")
    UserResponse toResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserProfile(UpdateUserProfileRequest request, @MappingTarget User user);
}
