package project.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.entity.Vote;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, UUID> {
}
