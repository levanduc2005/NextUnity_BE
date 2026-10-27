package project.course.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.course.entity.Module;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module, UUID> {
    @EntityGraph(attributePaths = {"lessons", "lessons.quiz", "course"})
    List<Module> findByCourseIdOrderByOrderIndexAsc(UUID courseId);

    @Override
    @EntityGraph(attributePaths = {"lessons", "lessons.quiz", "course"})
    Optional<Module> findById(UUID id);

    int countByCourseId(UUID courseId);
}
