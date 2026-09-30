package project.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.quiz.dto.question.request.CreateQuestionRequest;
import project.quiz.dto.question.request.QuestionOptionRequest;
import project.quiz.dto.question.request.UpdateQuestionRequest;
import project.quiz.dto.question.response.QuestionAdminResponse;
import project.quiz.dto.question.response.QuestionOptionAdminResponse;
import project.quiz.dto.question.response.TakeOptionResponse;
import project.quiz.dto.question.response.TakeQuestionResponse;
import project.quiz.entity.Question;
import project.quiz.entity.QuestionOption;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    // ==========================================
    // QUESTION MAPPINGS
    // ==========================================

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "options", ignore = true)
    @Mapping(target = "orderIndex", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Question toEntity(CreateQuestionRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    @Mapping(target = "options", ignore = true)
    @Mapping(target = "orderIndex", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateQuestionRequest request, @MappingTarget Question question);

    QuestionAdminResponse toAdminResponse(Question question);

    TakeQuestionResponse toTakeQuestionResponse(Question question);

    // ==========================================
    // OPTION MAPPINGS
    // ==========================================

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "orderIndex", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    QuestionOption toEntity(QuestionOptionRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "orderIndex", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(QuestionOptionRequest request, @MappingTarget QuestionOption option);

    QuestionOptionAdminResponse toAdminResponse(QuestionOption option);

    TakeOptionResponse toTakeOptionResponse(QuestionOption option);
}
