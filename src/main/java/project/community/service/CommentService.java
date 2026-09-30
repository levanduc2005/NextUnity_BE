package project.community.service;

import project.community.dto.comment.request.CreateCommentRequest;
import project.community.dto.comment.request.UpdateCommentRequest;
import project.community.dto.comment.response.CommentResponse;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentResponse createComment(CreateCommentRequest request);
    List<CommentResponse> getCommentsByPostId(UUID postId);
    CommentResponse updateComment(UUID id, UpdateCommentRequest request);
    void deleteComment(UUID id);
}
