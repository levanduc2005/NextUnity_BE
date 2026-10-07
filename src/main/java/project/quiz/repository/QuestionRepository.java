package project.quiz.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.quiz.entity.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    @EntityGraph(attributePaths = { "options" })
    List<Question> findByQuizIdOrderByOrderIndexAsc(UUID quizId);

    @Override
    @EntityGraph(attributePaths = { "options" })
    Optional<Question> findById(UUID id);

    int countByQuizId(UUID quizId);
}
