package project.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.course.dto.module.request.CreateModuleRequest;
import project.course.dto.module.request.UpdateModuleRequest;
import project.course.dto.module.response.ModuleResponse;
import project.course.entity.Module;

@Mapper(componentModel = "spring", uses = { LessonMapper.class })
public interface ModuleMapper {

    Module toEntity(CreateModuleRequest request);

    @Mapping(target = "courseId", source = "course.id")
    ModuleResponse toResponse(Module module);

    void updateEntity(UpdateModuleRequest request, @MappingTarget Module module);
}
