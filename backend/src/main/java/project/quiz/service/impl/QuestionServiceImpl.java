package project.quiz.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.quiz.dto.question.request.CreateQuestionRequest;
import project.quiz.dto.question.request.QuestionOptionRequest;
import project.quiz.dto.question.request.ReorderQuestionsRequest;
import project.quiz.dto.question.request.UpdateQuestionRequest;
import project.quiz.dto.question.response.QuestionAdminResponse;
import project.quiz.entity.Question;
import project.quiz.entity.QuestionOption;
import project.quiz.entity.Quiz;
import project.quiz.entity.enums.QuestionType;
import project.quiz.mapper.QuestionMapper;
import project.quiz.repository.QuestionRepository;
import project.quiz.repository.QuizRepository;
import project.quiz.service.QuestionService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionServiceImpl implements QuestionService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Override
    @Transactional
    public QuestionAdminResponse addQuestion(UUID quizId, CreateQuestionRequest request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        int correctCount = (int) request.options().stream()
                .filter(otp -> Boolean.TRUE.equals(otp.isCorrect()))
                .count();

        if (request.type() == QuestionType.SINGLE_CHOICE && correctCount != 1) {
            throw new AppException(ErrorCode.INVALID_QUESTION_OPTION_COUNT);
        }
        if (request.type() == QuestionType.MULTIPLE_CHOICE && correctCount < 1) {
            throw new AppException(ErrorCode.INVALID_QUESTION_OPTION_COUNT);
        }
        Question question = questionMapper.toEntity(request);
        question.setQuiz(quiz);
        question.setOrderIndex(quiz.getQuestions().size() + 1);

        for (int i = 0; i < request.options().size(); i++) {
            QuestionOptionRequest optReq = request.options().get(i);
            QuestionOption option = questionMapper.toEntity(optReq);
            option.setOrderIndex(i + 1);
            question.addOption(option);
        }

        return questionMapper.toAdminResponse(questionRepository.save(question));
    }

    @Override
    @Transactional
    public QuestionAdminResponse updateQuestion(UUID questionId, UpdateQuestionRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_FOUND));

        // 1. Validate tính hợp lệ của số lượng đáp án đúng
        int correctCount = (int) request.options().stream()
                .filter(opt -> Boolean.TRUE.equals(opt.isCorrect()))
                .count();

        if (request.type() == QuestionType.SINGLE_CHOICE && correctCount != 1) {
            throw new AppException(ErrorCode.INVALID_QUESTION_OPTION_COUNT);
        }
        if (request.type() == QuestionType.MULTIPLE_CHOICE && correctCount < 1) {
            throw new AppException(ErrorCode.INVALID_QUESTION_OPTION_COUNT);
        }

        // 2. Cập nhật thông tin chính của Question
        questionMapper.updateEntity(request, question);

        // 3. Đồng bộ danh sách Options (Atomic Sync theo chuẩn Aggregate Root)
        Set<UUID> requestOptionIds = request.options().stream()
                .map(QuestionOptionRequest::id)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Xóa các options không còn nằm trong payload gửi lên
        List<QuestionOption> toRemove = question.getOptions().stream()
                .filter(opt -> opt.getId() != null && !requestOptionIds.contains(opt.getId()))
                .toList();
        toRemove.forEach(question::removeOption);

        // Map các options hiện có để cập nhật
        Map<UUID, QuestionOption> existingMap = question.getOptions().stream()
                .filter(opt -> opt.getId() != null)
                .collect(Collectors.toMap(QuestionOption::getId, Function.identity()));

        // Duyệt danh sách mới: cập nhật option cũ hoặc thêm mới, đồng thời cập nhật orderIndex
        for (int i = 0; i < request.options().size(); i++) {
            QuestionOptionRequest optReq = request.options().get(i);
            if (optReq.id() != null && existingMap.containsKey(optReq.id())) {
                QuestionOption existingOpt = existingMap.get(optReq.id());
                questionMapper.updateEntity(optReq, existingOpt);
                existingOpt.setOrderIndex(i + 1);
            } else {
                QuestionOption newOpt = questionMapper.toEntity(optReq);
                newOpt.setOrderIndex(i + 1);
                question.addOption(newOpt);
            }
        }

        question = questionRepository.save(question);
        return questionMapper.toAdminResponse(question);
    }

    @Override
    @Transactional
    public void reorderQuestions(UUID quizId, ReorderQuestionsRequest request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new AppException(ErrorCode.QUIZ_NOT_FOUND));

        List<Question> questions = quiz.getQuestions();

        if (questions.size() != request.questionIds().size()) {
            throw new AppException(ErrorCode.INVALID_QUESTION_REORDER);
        }

        Map<UUID, Question> questionMap = questions.stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        for (int i = 0; i < request.questionIds().size(); i++) {
            UUID questionId = request.questionIds().get(i);
            Question question = questionMap.get(questionId);
            if (question == null) {
                throw new AppException(ErrorCode.QUESTION_NOT_FOUND);
            }
            question.setOrderIndex(i + 1);
        }

        questionRepository.saveAll(questions);
    }

    @Override
    @Transactional
    public void deleteQuestion(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new AppException(ErrorCode.QUESTION_NOT_FOUND));

        question.getQuiz().removeQuestion(question);

        questionRepository.delete(question);
    }
}
