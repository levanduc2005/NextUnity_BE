package project.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.course.entity.Enrollment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    List<Enrollment> findByUserIdOrderByCreatedAtDesc(UUID userId);
    Optional<Enrollment> findByUserIdAndCourseId(UUID userId, UUID courseId);
    boolean existsByUserIdAndCourseId(UUID userId, UUID courseId);
}
