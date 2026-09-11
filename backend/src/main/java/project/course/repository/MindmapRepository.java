package project.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.course.entity.Mindmap;

import java.util.Optional;
import java.util.UUID;

public interface MindmapRepository extends JpaRepository<Mindmap, UUID> {
    Optional<Mindmap> findByLessonId(UUID lessonId);
}
