package project.community.dto.community.response;

import project.community.entity.enums.CommunityRole;
import java.time.Instant;
import java.util.UUID;

public record CommunityMemberResponse(
        UUID id,
        UUID userId,
        String username,
        String email,
        CommunityRole role,
        Instant joinedAt
) {}
