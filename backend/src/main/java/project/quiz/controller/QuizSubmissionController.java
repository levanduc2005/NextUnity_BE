package project.quiz.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.quiz.dto.submission.request.SubmitQuizRequest;
import project.quiz.dto.submission.response.QuizResultResponse;
import project.quiz.dto.submission.response.QuizSubmissionSummaryResponse;
import project.quiz.service.QuizSubmissionService;
import project.shared.response.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class QuizSubmissionController {

    private final QuizSubmissionService quizSubmissionService;

    @PostMapping("/api/quizzes/{quizId}/submit")
    public ApiResponse<QuizResultResponse> submitQuiz(
            @PathVariable UUID quizId,
            @Valid @RequestBody SubmitQuizRequest request
    ) {
        QuizResultResponse response = quizSubmissionService.submitQuiz(quizId, request);
        return ApiResponse.success(response, "Nộp bài kiểm tra thành công");
    }

    @GetMapping("/api/quizzes/{quizId}/my-submissions")
    public ApiResponse<List<QuizSubmissionSummaryResponse>> getMySubmissions(@PathVariable UUID quizId) {
        List<QuizSubmissionSummaryResponse> response = quizSubmissionService.getMySubmissions(quizId);
        return ApiResponse.success(response, "Lấy danh sách bài nộp của tôi thành công");
    }

    @GetMapping("/api/quizzes/{quizId}/submissions")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ApiResponse<List<QuizSubmissionSummaryResponse>> getQuizSubmissionsForInstructor(@PathVariable UUID quizId) {
        List<QuizSubmissionSummaryResponse> response = quizSubmissionService.getQuizSubmissionsForInstructor(quizId);
        return ApiResponse.success(response, "Lấy danh sách học viên nộp bài thành công");
    }

    @GetMapping("/api/submissions/{submissionId}")
    public ApiResponse<QuizResultResponse> getSubmissionDetail(@PathVariable UUID submissionId) {
        QuizResultResponse response = quizSubmissionService.getSubmissionDetail(submissionId);
        return ApiResponse.success(response, "Lấy chi tiết kết quả bài nộp thành công");
    }
}
