package project.community.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.community.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    @EntityGraph(attributePaths = {"author", "reactions", "reactions.user"})
    List<Comment> findByPostIdOrderByCreatedAtAsc(UUID postId);

    @Override
    @EntityGraph(attributePaths = {"author", "reactions", "reactions.user"})
    Optional<Comment> findById(UUID id);
}
