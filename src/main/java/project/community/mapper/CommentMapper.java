package project.community.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import project.community.dto.comment.request.CreateCommentRequest;
import project.community.dto.comment.request.UpdateCommentRequest;
import project.community.dto.comment.response.CommentResponse;
import project.community.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toEntity(CreateCommentRequest request);

    void updateComment(UpdateCommentRequest request, @MappingTarget Comment comment);

    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "authorId", expression = "java(comment.getDeletedAt() == null ? comment.getAuthor().getId() : null)")
    @Mapping(target = "authorName", expression = "java(comment.getDeletedAt() == null && comment.getAuthor() != null ? (comment.getAuthor().getFullName() != null && !comment.getAuthor().getFullName().isBlank() ? comment.getAuthor().getFullName() : \"[deleted]\") : \"[deleted]\")")
    @Mapping(target = "authorAvatar", expression = "java(comment.getDeletedAt() == null && comment.getAuthor() != null ? comment.getAuthor().getAvatarUrl() : null)")
    @Mapping(target = "content", expression = "java(comment.getDeletedAt() == null ? comment.getContent() : \"[bình luận đã bị xóa]\")")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "reactionCount", expression = "java(comment.getReactions() != null ? comment.getReactions().size() : 0)")
    @Mapping(target = "replies", ignore = true)
    CommentResponse toResponse(Comment comment);
}
