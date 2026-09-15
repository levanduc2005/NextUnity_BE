package project.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.course.dto.enrollment.response.EnrollmentResponse;
import project.course.entity.Enrollment;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseTitle", source = "course.title")
    @Mapping(target = "courseDescription", source = "course.description")
    @Mapping(target = "courseThumbnailUrl", source = "course.thumbnailUrl")
    @Mapping(target = "courseDifficulty", source = "course.difficulty")
    @Mapping(target = "totalLessons", source = "course.totalLessons")
    EnrollmentResponse toResponse(Enrollment enrollment);
}
