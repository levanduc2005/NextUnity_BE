package project.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.course.dto.progress.response.LessonProgressResponse;
import project.course.entity.LessonProgress;

@Mapper(componentModel = "spring")
public interface LessonProgressMapper {

    @Mapping(target = "lessonId", source = "progress.lesson.id")
    @Mapping(target = "lessonTitle", source = "progress.lesson.title")
    @Mapping(target = "courseProgressPercent", source = "courseProgressPercent")
    LessonProgressResponse toResponse(LessonProgress progress, Integer courseProgressPercent);

    @Mapping(target = "lessonId", source = "progress.lesson.id")
    @Mapping(target = "lessonTitle", source = "progress.lesson.title")
    @Mapping(target = "courseProgressPercent", ignore = true)
    LessonProgressResponse toResponse(LessonProgress progress);
}
