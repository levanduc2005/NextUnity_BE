package project.course.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.course.entity.Course;
import project.course.entity.Enrollment;
import project.course.entity.enums.CourseCategory;
import project.course.entity.enums.CourseDifficulty;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    @EntityGraph(attributePaths = {"creator"})
    @Query("""
        SELECT c FROM Course c WHERE
        (:category IS NULL OR c.category = :category) AND
        (:difficulty IS NULL OR c.difficulty = :difficulty) AND
        (CAST(:search AS string) IS NULL OR 
         LOWER(c.title) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%')) OR 
         LOWER(c.code) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%')))
    """)
    Page<Course> searchCourses(
            @Param("category") CourseCategory category,
            @Param("difficulty") CourseDifficulty difficulty,
            @Param("search") String search,
            Pageable pageable
    );

    @Override
    @EntityGraph(attributePaths = {"creator"})
    Optional<Course> findById(UUID id);

    @EntityGraph(attributePaths = {"creator"})
    List<Course> findByCreatorIdOrderByCreatedAtDesc(UUID creatorId);

    @EntityGraph(attributePaths = {"creator"})
    @Query("""
        SELECT c FROM Course c
        ORDER BY (
            SELECT COUNT(e.id) FROM Enrollment e
            WHERE e.course = c AND e.createdAt >= :since
        ) DESC, c.createdAt DESC
    """)
    List<Course> findPopularCourses(@Param("since") Instant since, Pageable pageable);
}
