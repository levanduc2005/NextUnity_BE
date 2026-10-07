package project.course.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.course.entity.Module;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module, UUID> {
    @EntityGraph(attributePaths = {"lessons"})
    List<Module> findByCourseIdOrderByOrderIndexAsc(UUID courseId);

    @Override
    @EntityGraph(attributePaths = {"lessons"})
    Optional<Module> findById(UUID id);
}
