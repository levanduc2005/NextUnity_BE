package project.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.entity.Tag;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByName(String name);
}
