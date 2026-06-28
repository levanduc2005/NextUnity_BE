package project.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.auth.entity.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);

    boolean existsByName(String name);
}
