package project.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.course.entity.Lesson;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    List<Lesson> findByModuleIdOrderByOrderIndexAsc(UUID moduleId);
    List<Lesson> findByModule_Course_IdOrderByOrderIndexAsc(UUID courseId);
    long countByModule_Course_Id(UUID courseId);
}
