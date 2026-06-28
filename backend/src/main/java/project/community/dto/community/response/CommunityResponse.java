package project.community.dto.community.response;

import java.time.Instant;
import java.util.UUID;

public record CommunityResponse(
        UUID id,
        String name,
        String description,
        UUID creatorId,
        String creatorName,
        long memberCount,
        Instant createdAt
) {}
