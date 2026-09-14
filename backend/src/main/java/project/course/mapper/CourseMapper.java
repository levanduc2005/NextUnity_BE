package project.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.course.dto.course.request.CreateCourseRequest;
import project.course.dto.course.request.UpdateCourseRequest;
import project.course.dto.course.response.CourseDetailResponse;
import project.course.dto.course.response.CourseSummaryResponse;
import project.course.entity.Course;

@Mapper(componentModel = "spring", uses = {ModuleMapper.class})
public interface CourseMapper {

    Course toEntity(CreateCourseRequest request);

    @Mapping(target = "creatorId", source = "creator.id")
    @Mapping(target = "creatorName", source = "creator.name")
    CourseSummaryResponse toSummaryResponse(Course course);

    @Mapping(target = "creatorId", source = "creator.id")
    @Mapping(target = "creatorName", source = "creator.name")
    CourseDetailResponse toDetailResponse(Course course);

    void updateEntity(UpdateCourseRequest request, @MappingTarget Course course);
}
