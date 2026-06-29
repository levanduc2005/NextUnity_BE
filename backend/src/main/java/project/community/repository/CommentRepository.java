package project.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.entity.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByPostIdOrderByCreatedAtAsc(UUID postId);
}
