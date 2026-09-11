package project.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.course.entity.Flashcard;

import java.util.List;
import java.util.UUID;

public interface FlashcardRepository extends JpaRepository<Flashcard, UUID> {
    List<Flashcard> findByLessonIdOrderByOrderIndexAsc(UUID lessonId);
}
