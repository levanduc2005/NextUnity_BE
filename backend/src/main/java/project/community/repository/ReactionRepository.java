package project.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.entity.Reaction;

import java.util.Optional;
import java.util.UUID;

public interface ReactionRepository extends JpaRepository<Reaction, UUID> {
    Optional<Reaction> findByPost_IdAndUser_Id(UUID postId, UUID userId);
    Optional<Reaction> findByComment_IdAndUser_Id(UUID commentId, UUID userId);
}