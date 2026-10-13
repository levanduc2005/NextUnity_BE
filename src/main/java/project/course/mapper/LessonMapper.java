package project.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.course.dto.lesson.request.CreateLessonRequest;
import project.course.dto.lesson.request.UpdateLessonRequest;
import project.course.dto.lesson.response.LessonDetailResponse;
import project.course.dto.lesson.response.LessonSummaryResponse;
import project.course.entity.Lesson;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson toEntity(CreateLessonRequest request);

    @Mapping(target = "typeName", source = "type.displayName")
    LessonSummaryResponse toSummaryResponse(Lesson lesson);

    @Mapping(target = "moduleId", source = "module.id")
    @Mapping(target = "typeName", source = "type.displayName")
    LessonDetailResponse toDetailResponse(Lesson lesson);

    void updateEntity(UpdateLessonRequest request, @MappingTarget Lesson lesson);
}
