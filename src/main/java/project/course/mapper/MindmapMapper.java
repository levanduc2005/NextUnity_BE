package project.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.course.dto.mindmap.request.SaveMindmapRequest;
import project.course.dto.mindmap.response.MindmapResponse;
import project.course.entity.Mindmap;

@Mapper(componentModel = "spring")
public interface MindmapMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Mindmap toEntity(SaveMindmapRequest request);

    @Mapping(target = "lessonId", source = "lesson.id")
    MindmapResponse toResponse(Mindmap mindmap);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(SaveMindmapRequest request, @MappingTarget Mindmap mindmap);
}
