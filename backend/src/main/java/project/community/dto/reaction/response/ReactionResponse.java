package project.community.dto.reaction.response;

import project.community.entity.enums.ReactionType;
import java.util.Map;
import java.util.UUID;

public record ReactionResponse(
        UUID postId,
        UUID commentId,
        ReactionType currentUserReaction,
        long totalReactions,
        Map<ReactionType, Long> reactionDetails
) {}
