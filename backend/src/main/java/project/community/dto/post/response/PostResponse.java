package project.community.dto.post.response;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record PostResponse(
        UUID id,
        UUID communityId,
        String communityName,
        UUID authorId,
        String authorName,
        String title,
        String content,
        Set<String> tags,
        long voteCount,
        long commentCount,
        Instant createdAt,
        Instant updatedAt
) {}
