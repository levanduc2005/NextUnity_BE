package project.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.course.dto.course.request.CreateCourseRequest;
import project.course.dto.course.request.UpdateCourseRequest;
import project.course.dto.course.response.CourseDetailResponse;
import project.course.dto.course.response.CourseSummaryResponse;
import project.course.dto.lesson.response.LessonSummaryResponse;
import project.course.dto.module.response.ModuleResponse;
import project.course.entity.Course;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = { ModuleMapper.class })
public interface CourseMapper {

    Course toEntity(CreateCourseRequest request);

    @Mapping(target = "creatorId", source = "creator.id")
    @Mapping(target = "creatorName", source = "creator.fullName")
    @Mapping(target = "categoryName", source = "category.displayName")
    @Mapping(target = "difficultyName", source = "difficulty.displayName")
    CourseSummaryResponse toSummaryResponse(Course course);

    @Mapping(target = "creatorId", source = "creator.id")
    @Mapping(target = "creatorName", source = "creator.fullName")
    @Mapping(target = "categoryName", source = "category.displayName")
    @Mapping(target = "difficultyName", source = "difficulty.displayName")
    @Mapping(target = "isEnrolled", constant = "false")
    @Mapping(target = "myProgressPercent", ignore = true)
    CourseDetailResponse toDetailResponse(Course course);

    @Mapping(target = "creatorId", source = "course.creator.id")
    @Mapping(target = "creatorName", source = "course.creator.fullName")
    @Mapping(target = "categoryName", source = "course.category.displayName")
    @Mapping(target = "difficultyName", source = "course.difficulty.displayName")
    @Mapping(target = "isPremium", source = "course.isPremium")
    @Mapping(target = "isEnrolled", source = "isEnrolled")
    @Mapping(target = "myProgressPercent", source = "myProgressPercent")
    CourseDetailResponse toDetailResponse(Course course, Boolean isEnrolled, Integer myProgressPercent);

    default CourseDetailResponse toDetailResponse(Course course, Boolean isEnrolled, Integer myProgressPercent,
            Set<UUID> completedLessonIds) {
        CourseDetailResponse response = toDetailResponse(course, isEnrolled, myProgressPercent);
        if (response.modules() == null) {
            return response;
        }

        final Set<UUID> completedSet = completedLessonIds != null ? completedLessonIds : Set.of();

        List<ModuleResponse> updatedModules = response.modules().stream()
                .map(module -> new ModuleResponse(
                        module.id(),
                        module.courseId(),
                        module.title(),
                        module.description(),
                        module.orderIndex(),
                        module.lessons() == null ? List.of()
                                : module.lessons().stream()
                                        .map(lesson -> new LessonSummaryResponse(
                                                lesson.id(),
                                                lesson.title(),
                                                lesson.orderIndex(),
                                                lesson.type(),
                                                lesson.typeName(),
                                                lesson.estimatedMinutes(),
                                                completedSet.contains(lesson.id()),
                                                lesson.quizId()))
                                        .toList()))
                .toList();

        return new CourseDetailResponse(
                response.id(),
                response.code(),
                response.title(),
                response.description(),
                response.category(),
                response.categoryName(),
                response.difficulty(),
                response.difficultyName(),
                response.thumbnailUrl(),
                response.totalLessons(),
                response.isPremium(),
                response.creatorId(),
                response.creatorName(),
                response.createdAt(),
                response.isEnrolled(),
                response.myProgressPercent(),
                updatedModules);
    }

    void updateEntity(UpdateCourseRequest request, @MappingTarget Course course);
}
