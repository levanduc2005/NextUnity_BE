package project.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.identity.entity.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
}
