package project.community.dto.post.response;

import project.community.entity.enums.ReactionType;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public record PostResponse(
        UUID id,
        UUID communityId,
        String communityName,
        UUID authorId,
        String authorName,
        String authorAvatar,
        String authorUniversity,
        String title,
        String content,
        Set<String> tags,
        long reactionCount,
        Map<ReactionType, Long> reactionDetails,
        ReactionType currentUserReaction,
        long commentCount,
        Instant createdAt,
        Instant updatedAt
) {
    public PostResponse {
        reactionDetails = reactionDetails == null ? new HashMap<>() : reactionDetails;
    }
}
