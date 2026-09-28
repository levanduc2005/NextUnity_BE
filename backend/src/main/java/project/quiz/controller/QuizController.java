package project.quiz.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.quiz.dto.quiz.request.CreateQuizRequest;
import project.quiz.dto.quiz.request.UpdateQuizRequest;
import project.quiz.dto.quiz.response.QuizAdminResponse;
import project.quiz.dto.quiz.response.TakeQuizResponse;
import project.quiz.service.QuizService;
import project.shared.response.ApiResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/api/lessons/{lessonId}/quiz/manage")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ApiResponse<QuizAdminResponse> getQuizAdmin(@PathVariable UUID lessonId) {
        QuizAdminResponse response = quizService.getQuizAdmin(lessonId);
        return ApiResponse.success(response, "Lấy thông tin bài kiểm tra (Admin) thành công");
    }

    @GetMapping("/api/lessons/{lessonId}/quiz")
    public ApiResponse<TakeQuizResponse> getQuizForStudent(@PathVariable UUID lessonId) {
        TakeQuizResponse response = quizService.getQuizForStudent(lessonId);
        return ApiResponse.success(response, "Lấy đề thi thành công");
    }

    @PostMapping("/api/lessons/{lessonId}/quiz")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ApiResponse<QuizAdminResponse> createQuiz(
            @PathVariable UUID lessonId,
            @Valid @RequestBody CreateQuizRequest request
    ) {
        QuizAdminResponse response = quizService.createQuiz(lessonId, request);
        return ApiResponse.success(response, "Tạo bài kiểm tra thành công");
    }

    @PutMapping("/api/quizzes/{quizId}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ApiResponse<QuizAdminResponse> updateQuiz(
            @PathVariable UUID quizId,
            @Valid @RequestBody UpdateQuizRequest request
    ) {
        QuizAdminResponse response = quizService.updateQuiz(quizId, request);
        return ApiResponse.success(response, "Cập nhật bài kiểm tra thành công");
    }

    @PatchMapping("/api/quizzes/{quizId}/publish")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ApiResponse<QuizAdminResponse> togglePublish(@PathVariable UUID quizId) {
        QuizAdminResponse response = quizService.togglePublish(quizId);
        return ApiResponse.success(response, "Cập nhật trạng thái xuất bản thành công");
    }

    @DeleteMapping("/api/quizzes/{quizId}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ApiResponse<Void> deleteQuiz(@PathVariable UUID quizId) {
        quizService.deleteQuiz(quizId);
        return ApiResponse.success(null, "Xóa bài kiểm tra thành công");
    }
}
