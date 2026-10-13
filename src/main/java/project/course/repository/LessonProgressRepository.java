package project.course.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.course.entity.LessonProgress;
import project.course.entity.enums.LessonProgressStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, UUID> {
    @EntityGraph(attributePaths = {"lesson"})
    Optional<LessonProgress> findByUserIdAndLessonId(UUID userId, UUID lessonId);

    @EntityGraph(attributePaths = {"lesson"})
    List<LessonProgress> findByUserIdAndLesson_Module_Course_Id(UUID userId, UUID courseId);

    int countByUserIdAndLesson_Module_Course_IdAndStatus(UUID userId, UUID courseId, LessonProgressStatus status);
}
