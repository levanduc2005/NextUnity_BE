package project.quiz.service;

import project.quiz.dto.quiz.request.CreateQuizRequest;
import project.quiz.dto.quiz.request.UpdateQuizRequest;
import project.quiz.dto.quiz.response.QuizAdminResponse;
import project.quiz.dto.quiz.response.TakeQuizResponse;

import java.util.UUID;

public interface QuizService {

    /**
     * Lấy chi tiết bài kiểm tra dành cho Giảng viên / Quản trị viên (có đáp án).
     */
    QuizAdminResponse getQuizAdmin(UUID lessonId);

    /**
     * Lấy đề thi cho Học viên bắt đầu làm bài (đã ẩn toàn bộ đáp án đúng - Anti-Cheat).
     */
    TakeQuizResponse getQuizForStudent(UUID lessonId);

    /**
     * Tạo bài kiểm tra gắn với một bài học.
     */
    QuizAdminResponse createQuiz(UUID lessonId, CreateQuizRequest request);

    /**
     * Cập nhật thông tin bài kiểm tra (tiêu đề, thời gian, điểm đạt,...).
     */
    QuizAdminResponse updateQuiz(UUID quizId, UpdateQuizRequest request);

    /**
     * Bật / tắt trạng thái xuất bản đề thi (DRAFT <-> PUBLISHED).
     * Chỉ cho phép xuất bản khi đề thi đã có ít nhất 1 câu hỏi.
     */
    QuizAdminResponse togglePublish(UUID quizId);

    /**
     * Xóa bài kiểm tra (Cascade xóa toàn bộ câu hỏi và lựa chọn con).
     */
    void deleteQuiz(UUID quizId);
}
