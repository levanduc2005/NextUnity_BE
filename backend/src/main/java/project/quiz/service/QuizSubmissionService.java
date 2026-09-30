package project.quiz.service;

import project.quiz.dto.submission.request.SubmitQuizRequest;
import project.quiz.dto.submission.response.QuizResultResponse;
import project.quiz.dto.submission.response.QuizSubmissionSummaryResponse;

import java.util.List;
import java.util.UUID;

public interface QuizSubmissionService {

    /**
     * Học viên nộp bài làm: Backend tự động chấm điểm, tính tỷ lệ đạt/trượt và lưu kết quả.
     */
    QuizResultResponse submitQuiz(UUID quizId, SubmitQuizRequest request);

    /**
     * Học viên xem lịch sử các lần nộp bài của chính mình.
     */
    List<QuizSubmissionSummaryResponse> getMySubmissions(UUID quizId);

    /**
     * Quản trị viên xem danh sách tất cả học viên đã nộp bài.
     */
    List<QuizSubmissionSummaryResponse> getQuizSubmissionsForAdmin(UUID quizId);

    /**
     * Xem kết quả chi tiết từng câu hỏi của một lượt nộp bài.
     */
    QuizResultResponse getSubmissionDetail(UUID submissionId);
}
