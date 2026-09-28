package project.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.quiz.entity.QuizSubmission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, UUID> {
    Optional<QuizSubmission> findTopByQuizIdAndUserIdOrderBySubmittedAtDesc(UUID quizId, UUID userId);
    List<QuizSubmission> findByQuizIdAndUserIdOrderBySubmittedAtDesc(UUID quizId, UUID userId);
    List<QuizSubmission> findByQuizIdOrderBySubmittedAtDesc(UUID quizId);
    int countByQuizIdAndUserId(UUID quizId, UUID userId);
}
