package project.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.quiz.entity.QuizSubmissionOption;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizSubmissionOptionRepository extends JpaRepository<QuizSubmissionOption, UUID> {
    List<QuizSubmissionOption> findBySubmissionId(UUID submissionId);
}
