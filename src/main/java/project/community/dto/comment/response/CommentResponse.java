package project.community.dto.comment.response;

import project.community.entity.enums.ReactionType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record CommentResponse(
        UUID id,
        UUID postId,
        UUID authorId,
        String authorName,
        String authorAvatar,
        String content,
        UUID parentId,
        long reactionCount,
        Map<ReactionType, Long> reactionDetails,
        ReactionType currentUserReaction,
        List<CommentResponse> replies,
        Instant createdAt,
        Instant updatedAt
) {
    public CommentResponse {
        replies = replies == null ? new ArrayList<>() : new ArrayList<>(replies);
        reactionDetails = reactionDetails == null ? new HashMap<>() : reactionDetails;
    }
}
