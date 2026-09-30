package project.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.course.dto.flashcard.request.CreateFlashcardRequest;
import project.course.dto.flashcard.request.UpdateFlashcardRequest;
import project.course.dto.flashcard.response.FlashcardResponse;
import project.course.entity.Flashcard;

@Mapper(componentModel = "spring")
public interface FlashcardMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "orderIndex", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Flashcard toEntity(CreateFlashcardRequest request);

    @Mapping(target = "lessonId", source = "lesson.id")
    FlashcardResponse toResponse(Flashcard flashcard);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "orderIndex", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateFlashcardRequest request, @MappingTarget Flashcard flashcard);
}
