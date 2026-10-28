package project.community.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.community.dto.comment.request.CreateCommentRequest;
import project.community.dto.comment.request.UpdateCommentRequest;
import project.community.dto.comment.response.CommentResponse;
import project.community.entity.Comment;
import project.community.entity.Reaction;
import project.community.entity.enums.ReactionType;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toEntity(CreateCommentRequest request);

    void updateComment(UpdateCommentRequest request, @MappingTarget Comment comment);

    @Mapping(target = "postId", source = "comment.post.id")
    @Mapping(target = "authorId", expression = "java(comment.getDeletedAt() == null && comment.getAuthor() != null ? comment.getAuthor().getId() : null)")
    @Mapping(target = "authorName", expression = "java(comment.getDeletedAt() == null && comment.getAuthor() != null ? (comment.getAuthor().getFullName() != null && !comment.getAuthor().getFullName().isBlank() ? comment.getAuthor().getFullName() : \"[deleted]\") : \"[deleted]\")")
    @Mapping(target = "authorAvatar", expression = "java(comment.getDeletedAt() == null && comment.getAuthor() != null ? comment.getAuthor().getAvatarUrl() : null)")
    @Mapping(target = "content", expression = "java(comment.getDeletedAt() == null ? comment.getContent() : \"[bình luận đã bị xóa]\")")
    @Mapping(target = "parentId", source = "comment.parent.id")
    @Mapping(target = "reactionCount", expression = "java(comment.getReactions() != null ? comment.getReactions().size() : 0)")
    @Mapping(target = "reactionDetails", expression = "java(mapReactionDetails(comment.getReactions()))")
    @Mapping(target = "currentUserReaction", expression = "java(findUserReaction(comment.getReactions(), currentUserId))")
    @Mapping(target = "replies", ignore = true)
    CommentResponse toResponse(Comment comment, @Context UUID currentUserId);

    default CommentResponse toResponse(Comment comment) {
        return toResponse(comment, null);
    }

    default Map<ReactionType, Long> mapReactionDetails(List<Reaction> reactions) {
        if (reactions == null || reactions.isEmpty()) {
            return Map.of();
        }
        return reactions.stream()
                .collect(Collectors.groupingBy(Reaction::getReactionType, Collectors.counting()));
    }

    default ReactionType findUserReaction(List<Reaction> reactions, UUID currentUserId) {
        if (reactions == null || reactions.isEmpty() || currentUserId == null) {
            return null;
        }
        return reactions.stream()
                .filter(r -> r.getUser() != null && currentUserId.equals(r.getUser().getId()))
                .map(Reaction::getReactionType)
                .findFirst()
                .orElse(null);
    }
}
