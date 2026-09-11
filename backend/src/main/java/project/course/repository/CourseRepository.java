package project.course.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.course.entity.Course;
import project.course.entity.enums.CourseDifficulty;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    @Query("SELECT c FROM Course c WHERE " +
           "(:category IS NULL OR LOWER(c.category) = LOWER(:category)) AND " +
           "(:difficulty IS NULL OR c.difficulty = :difficulty) AND " +
           "(:search IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Course> findCourses(
            @Param("category") String category,
            @Param("difficulty") CourseDifficulty difficulty,
            @Param("search") String search,
            Pageable pageable
    );

    List<Course> findByCreatorIdOrderByCreatedAtDesc(UUID creatorId);
}
