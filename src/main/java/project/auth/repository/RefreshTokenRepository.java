package project.auth.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.auth.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    @EntityGraph(attributePaths = {"user", "user.role"})
    Optional<RefreshToken> findByToken(String token);
}
