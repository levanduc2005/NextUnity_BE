package project.user.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph(attributePaths = {"role"})
    Optional<User> findByEmail(String email);

    @Override
    @EntityGraph(attributePaths = {"role"})
    Optional<User> findById(UUID id);

    boolean existsByEmail(String email);
}
