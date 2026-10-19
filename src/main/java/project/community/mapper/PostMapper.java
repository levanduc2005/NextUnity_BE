package project.community.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.community.dto.post.request.CreatePostRequest;
import project.community.dto.post.request.UpdatePostRequest;
import project.community.dto.post.response.PostResponse;
import project.community.entity.Post;
import project.community.entity.Reaction;
import project.community.entity.Tag;
import project.community.entity.enums.ReactionType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toEntity(CreatePostRequest request);

    void updatePost(UpdatePostRequest request, @MappingTarget Post post);

    @Mapping(target = "communityId", ignore = true)
    @Mapping(target = "communityName", ignore = true)
    @Mapping(target = "authorId", source = "post.author.id")
    @Mapping(target = "authorName", expression = "java(post.getAuthor() != null ? post.getAuthor().getFullName() : null)")
    @Mapping(target = "authorAvatar", source = "post.author.avatarUrl")
    @Mapping(target = "authorUniversity", source = "post.author.university")
    @Mapping(target = "tags", expression = "java(mapTags(post.getTags()))")
    @Mapping(target = "reactionCount", expression = "java(post.getReactions() != null ? post.getReactions().size() : 0)")
    @Mapping(target = "reactionDetails", expression = "java(mapReactionDetails(post.getReactions()))")
    @Mapping(target = "currentUserReaction", expression = "java(findUserReaction(post.getReactions(), currentUserId))")
    @Mapping(target = "commentCount", expression = "java(post.getComments() != null ? post.getComments().size() : 0)")
    PostResponse toResponse(Post post, @Context UUID currentUserId);

    default PostResponse toResponse(Post post) {
        return toResponse(post, null);
    }

    default Set<String> mapTags(Set<Tag> tags) {
        if (tags == null)
            return Set.of();
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
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
