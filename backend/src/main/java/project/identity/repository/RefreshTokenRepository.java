package project.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.identity.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
}
