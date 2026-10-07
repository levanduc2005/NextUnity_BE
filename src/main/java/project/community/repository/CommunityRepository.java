package project.community.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.community.entity.Community;

import java.util.Optional;
import java.util.UUID;

public interface CommunityRepository extends JpaRepository<Community, UUID> {

    boolean existsByName(String name);

    @EntityGraph(attributePaths = {"creator", "members"})
    Optional<Community> findByName(String name);

    @Override
    @EntityGraph(attributePaths = {"creator", "members"})
    Optional<Community> findById(UUID id);
}
