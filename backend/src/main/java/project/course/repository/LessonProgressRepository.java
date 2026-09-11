package project.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.course.entity.LessonProgress;
import project.course.entity.enums.LessonProgressStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, UUID> {
    Optional<LessonProgress> findByUserIdAndLessonId(UUID userId, UUID lessonId);
    List<LessonProgress> findByUserIdAndLesson_Module_Course_Id(UUID userId, UUID courseId);
    long countByUserIdAndLesson_Module_Course_IdAndStatus(UUID userId, UUID courseId, LessonProgressStatus status);
}
