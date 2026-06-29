package project.community.dto.comment.response;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record CommentResponse(
        UUID id,
        UUID postId,
        UUID authorId,
        String authorName,
        String content,
        UUID parentId,
        long voteCount,
        List<CommentResponse> replies,
        Instant createdAt,
        Instant updatedAt
) {
    public CommentResponse {
        replies = replies == null ? new ArrayList<>() : new ArrayList<>(replies);
    }
}
