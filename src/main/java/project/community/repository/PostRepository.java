package project.community.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @EntityGraph(attributePaths = {"author", "tags"})
    @Query("""
        SELECT p FROM Post p
        WHERE p.deletedAt IS NULL
        ORDER BY (
            (
                (SELECT COUNT(r) FROM Reaction r WHERE r.post = p) * 1.0 +
                (SELECT COUNT(c) FROM Comment c WHERE c.post = p AND c.deletedAt IS NULL) * 2.0
            ) / (
                1.0 + (EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) - EXTRACT(EPOCH FROM p.createdAt)) / 86400.0
            )
        ) DESC, p.createdAt DESC
    """)
    List<Post> findTrendingPosts(Pageable pageable);
}
