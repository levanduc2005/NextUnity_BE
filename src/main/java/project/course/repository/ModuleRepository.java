package project.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.course.entity.Module;

import java.util.List;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<Module, UUID> {
    List<Module> findByCourseIdOrderByOrderIndexAsc(UUID courseId);
}
