package project.quiz.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.quiz.dto.question.request.CreateQuestionRequest;
import project.quiz.dto.question.request.ReorderQuestionsRequest;
import project.quiz.dto.question.request.UpdateQuestionRequest;
import project.quiz.dto.question.response.QuestionAdminResponse;
import project.quiz.service.QuestionService;
import project.shared.response.ApiResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/api/quizzes/{quizId}/questions")
    public ApiResponse<QuestionAdminResponse> addQuestion(
            @PathVariable UUID quizId,
            @Valid @RequestBody CreateQuestionRequest request
    ) {
        QuestionAdminResponse response = questionService.addQuestion(quizId, request);
        return ApiResponse.success(response, "Thêm câu hỏi thành công");
    }

    @PutMapping("/api/questions/{questionId}")
    public ApiResponse<QuestionAdminResponse> updateQuestion(
            @PathVariable UUID questionId,
            @Valid @RequestBody UpdateQuestionRequest request
    ) {
        QuestionAdminResponse response = questionService.updateQuestion(questionId, request);
        return ApiResponse.success(response, "Cập nhật câu hỏi thành công");
    }

    @PutMapping("/api/quizzes/{quizId}/questions/reorder")
    public ApiResponse<Void> reorderQuestions(
            @PathVariable UUID quizId,
            @Valid @RequestBody ReorderQuestionsRequest request
    ) {
        questionService.reorderQuestions(quizId, request);
        return ApiResponse.success(null, "Sắp xếp lại thứ tự câu hỏi thành công");
    }

    @DeleteMapping("/api/questions/{questionId}")
    public ApiResponse<Void> deleteQuestion(@PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
        return ApiResponse.success(null, "Xóa câu hỏi thành công");
    }
}
