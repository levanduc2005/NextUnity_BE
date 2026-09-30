package project.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.entity.Community;

import java.util.Optional;
import java.util.UUID;

public interface CommunityRepository extends JpaRepository<Community, UUID> {

    boolean existsByName(String name);

    Optional<Community> findByName(String name);
}
