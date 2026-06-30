package project.community.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.community.dto.reaction.response.ReactionResponse;
import project.community.entity.Comment;
import project.community.entity.Post;
import project.community.entity.Reaction;
import project.community.entity.enums.ReactionType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReactionMapper {

    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "currentUserReaction", source = "currentUserReaction")
    @Mapping(target = "totalReactions", expression = "java(post.getReactions() != null ? post.getReactions().size() : 0)")
    @Mapping(target = "reactionDetails", expression = "java(mapReactionDetails(post.getReactions()))")
    ReactionResponse toPostResponse(Post post, ReactionType currentUserReaction);

    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "commentId", source = "comment.id")
    @Mapping(target = "currentUserReaction", source = "currentUserReaction")
    @Mapping(target = "totalReactions", expression = "java(comment.getReactions() != null ? comment.getReactions().size() : 0)")
    @Mapping(target = "reactionDetails", expression = "java(mapReactionDetails(comment.getReactions()))")
    ReactionResponse toCommentResponse(Comment comment, ReactionType currentUserReaction);

    // Hàm phụ trợ để tự động đếm và nhóm các loại reaction
    default Map<ReactionType, Long> mapReactionDetails(List<Reaction> reactions) {
        if (reactions == null) {
            return Map.of();
        }
        return reactions.stream()
                .collect(Collectors.groupingBy(Reaction::getReactionType, Collectors.counting()));
    }
}
