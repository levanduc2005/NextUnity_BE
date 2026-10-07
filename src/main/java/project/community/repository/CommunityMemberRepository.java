package project.community.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import project.community.entity.CommunityMember;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, UUID> {
    boolean existsByCommunity_IdAndUser_Id(UUID communityId, UUID userId);

    @EntityGraph(attributePaths = {"user"})
    Optional<CommunityMember> findByCommunity_IdAndUser_Id(UUID communityId, UUID userId);

    @EntityGraph(attributePaths = {"user"})
    List<CommunityMember> findByCommunity_Id(UUID communityId);
}
