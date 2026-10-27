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

    @Mapping(target = "orderIndex", ignore = true)
    Lesson toEntity(CreateLessonRequest request);

    @Mapping(target = "typeName", source = "type.displayName")
    @Mapping(target = "isCompleted", constant = "false")
    @Mapping(target = "quizId", source = "quiz.id")
    LessonSummaryResponse toSummaryResponse(Lesson lesson);

    @Mapping(target = "typeName", source = "lesson.type.displayName")
    @Mapping(target = "isCompleted", source = "isCompleted")
    @Mapping(target = "quizId", source = "lesson.quiz.id")
    LessonSummaryResponse toSummaryResponse(Lesson lesson, Boolean isCompleted);

    @Mapping(target = "moduleId", source = "module.id")
    @Mapping(target = "typeName", source = "type.displayName")
    @Mapping(target = "isCompleted", constant = "false")
    @Mapping(target = "quizId", source = "quiz.id")
    LessonDetailResponse toDetailResponse(Lesson lesson);

    @Mapping(target = "moduleId", source = "lesson.module.id")
    @Mapping(target = "typeName", source = "lesson.type.displayName")
    @Mapping(target = "isCompleted", source = "isCompleted")
    @Mapping(target = "quizId", source = "lesson.quiz.id")
    LessonDetailResponse toDetailResponse(Lesson lesson, Boolean isCompleted);

    void updateEntity(UpdateLessonRequest request, @MappingTarget Lesson lesson);
}
