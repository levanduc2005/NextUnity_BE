package project.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.quiz.entity.Question;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findByQuizIdOrderByOrderIndexAsc(UUID quizId);
    int countByQuizId(UUID quizId);
}
