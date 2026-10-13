package project.course.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.course.entity.Enrollment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    @EntityGraph(attributePaths = {"course", "course.creator"})
    List<Enrollment> findByUserIdOrderByCreatedAtDesc(UUID userId);

    @EntityGraph(attributePaths = {"course", "course.creator"})
    Optional<Enrollment> findByUserIdAndCourseId(UUID userId, UUID courseId);

    @Override
    @EntityGraph(attributePaths = {"course", "course.creator"})
    Optional<Enrollment> findById(UUID id);

    boolean existsByUserIdAndCourseId(UUID userId, UUID courseId);
}
