package project.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.user.entity.User;
import project.user.entity.enums.SubscriptionTier;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @EntityGraph(attributePaths = {"role"})
    Optional<User> findByEmail(String email);

    @Override
    @EntityGraph(attributePaths = {"role"})
    Optional<User> findById(UUID id);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"role"})
    @Query("""
        SELECT u FROM User u WHERE
        (CAST(:roleName AS string) IS NULL OR UPPER(u.role.name) = UPPER(CAST(:roleName AS string))) AND
        (:subscriptionTier IS NULL OR u.subscriptionTier = :subscriptionTier) AND
        (CAST(:search AS string) IS NULL OR
         LOWER(u.fullName) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%')) OR
         LOWER(u.email) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%')) OR
         LOWER(COALESCE(u.studentId, '')) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%')))
    """)
    Page<User> searchUsers(
            @Param("roleName") String roleName,
            @Param("subscriptionTier") SubscriptionTier subscriptionTier,
            @Param("search") String search,
            Pageable pageable
    );
}
