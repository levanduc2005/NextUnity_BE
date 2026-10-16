package project.community.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.community.entity.Post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @EntityGraph(attributePaths = {"author", "tags"})
    List<Post> findByAuthorIdOrderByCreatedAtDesc(UUID authorId);

    @EntityGraph(attributePaths = {"author", "tags"})
    List<Post> findAllByOrderByCreatedAtDesc();

    @Override
    @EntityGraph(attributePaths = {"author", "tags"})
    Optional<Post> findById(UUID id);
}
