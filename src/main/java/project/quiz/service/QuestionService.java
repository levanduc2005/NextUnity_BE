package project.quiz.service;

import project.quiz.dto.question.request.CreateQuestionRequest;
import project.quiz.dto.question.request.ReorderQuestionsRequest;
import project.quiz.dto.question.request.UpdateQuestionRequest;
import project.quiz.dto.question.response.QuestionAdminResponse;

import java.util.UUID;

public interface QuestionService {

    /**
     * Thêm câu hỏi mới vào bài kiểm tra (kèm danh sách lựa chọn ban đầu).
     */
    QuestionAdminResponse addQuestion(UUID quizId, CreateQuestionRequest request);

    /**
     * Cập nhật toàn bộ câu hỏi và danh sách lựa chọn (Atomic Update).
     */
    QuestionAdminResponse updateQuestion(UUID questionId, UpdateQuestionRequest request);

    /**
     * Sắp xếp lại thứ tự danh sách câu hỏi trong bài kiểm tra.
     */
    void reorderQuestions(UUID quizId, ReorderQuestionsRequest request);

    /**
     * Xóa câu hỏi khỏi bài kiểm tra (tự động xóa toàn bộ options liên quan).
     */
    void deleteQuestion(UUID questionId);
}
