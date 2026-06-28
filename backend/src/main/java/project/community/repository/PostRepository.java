package project.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.entity.Post;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}
