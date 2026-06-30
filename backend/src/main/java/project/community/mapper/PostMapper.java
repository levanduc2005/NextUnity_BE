package project.community.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.community.dto.post.request.CreatePostRequest;
import project.community.dto.post.request.UpdatePostRequest;
import project.community.dto.post.response.PostResponse;
import project.community.entity.Post;
import project.community.entity.Tag;
import project.community.entity.enums.ReactionType;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toEntity(CreatePostRequest request);

    void updatePost(UpdatePostRequest request, @MappingTarget Post post);

    @Mapping(target = "communityId", source = "community.id")
    @Mapping(target = "communityName", source = "community.name")
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "authorName", source = "author.username")
    @Mapping(target = "tags", expression = "java(mapTags(post.getTags()))")
    @Mapping(target = "reactionCount", expression = "java(post.getReactions() != null ? post.getReactions().size() : 0)")
    @Mapping(target = "commentCount", expression = "java(post.getComments() != null ? post.getComments().size() : 0)")
    PostResponse toResponse(Post post);

    default Set<String> mapTags(Set<Tag> tags) {
        if (tags == null) return Set.of();
        return tags.stream().map(Tag::getName).collect(Collectors.toSet());
    }
}
