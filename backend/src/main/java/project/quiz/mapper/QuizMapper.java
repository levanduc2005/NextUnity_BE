package project.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.quiz.dto.quiz.request.CreateQuizRequest;
import project.quiz.dto.quiz.request.UpdateQuizRequest;
import project.quiz.dto.quiz.response.QuizAdminResponse;
import project.quiz.dto.quiz.response.TakeQuizResponse;
import project.quiz.entity.Quiz;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface QuizMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Quiz toEntity(CreateQuizRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateQuizRequest request, @MappingTarget Quiz quiz);

    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "totalQuestions", expression = "java(quiz.getQuestions() != null ? quiz.getQuestions().size() : 0)")
    QuizAdminResponse toAdminResponse(Quiz quiz);

    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "totalQuestions", expression = "java(quiz.getQuestions() != null ? quiz.getQuestions().size() : 0)")
    TakeQuizResponse toTakeQuizResponse(Quiz quiz);
}
