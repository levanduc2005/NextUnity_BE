package project.quiz.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.quiz.entity.Quiz;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    @EntityGraph(attributePaths = {"questions"})
    Optional<Quiz> findByLessonId(UUID lessonId);

    @Override
    @EntityGraph(attributePaths = {"questions"})
    Optional<Quiz> findById(UUID id);

    boolean existsByLessonId(UUID lessonId);
}
