package project.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.entity.CommunityMember;

import java.util.UUID;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, UUID> {
}
