package project.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.course.dto.enrollment.response.EnrollmentResponse;
import project.course.entity.Enrollment;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(target = "courseId", source = "enrollment.course.id")
    @Mapping(target = "courseCode", source = "enrollment.course.code")
    @Mapping(target = "courseTitle", source = "enrollment.course.title")
    @Mapping(target = "courseDescription", source = "enrollment.course.description")
    @Mapping(target = "courseThumbnailUrl", source = "enrollment.course.thumbnailUrl")
    @Mapping(target = "courseDifficulty", source = "enrollment.course.difficulty")
    @Mapping(target = "courseDifficultyName", source = "enrollment.course.difficulty.displayName")
    @Mapping(target = "totalLessons", source = "enrollment.course.totalLessons")
    @Mapping(target = "instructorName", source = "enrollment.course.creator.fullName")
    @Mapping(target = "currentLessonTitle", source = "currentLessonTitle")
    EnrollmentResponse toResponse(Enrollment enrollment, String currentLessonTitle);

    default EnrollmentResponse toResponse(Enrollment enrollment) {
        return toResponse(enrollment, (String) null);
    }
}
