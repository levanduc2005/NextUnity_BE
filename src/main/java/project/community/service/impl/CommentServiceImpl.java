package project.community.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.community.dto.comment.request.CreateCommentRequest;
import project.community.dto.comment.request.UpdateCommentRequest;
import project.community.dto.comment.response.CommentResponse;
import project.community.entity.Comment;
import project.community.entity.Post;
import project.community.mapper.CommentMapper;
import project.community.repository.CommentRepository;
import project.community.repository.PostRepository;
import project.community.service.CommentService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.entity.User;
import project.user.service.UserService;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserService userService;

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentResponse createComment(CreateCommentRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        User author = userService.getUserById(currentUserId);

        Comment comment = commentMapper.toEntity(request);
        comment.setAuthor(author);
        comment.setPost(post);

        // Nếu comment là reply của comment khác, thì set parent comment
        if(request.parentId()!=null){
            Comment parentComment = commentRepository.findById(request.parentId())
                    .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
            comment.setParent(parentComment);
        }

        return commentMapper.toResponse(commentRepository.save(comment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPostId(UUID postId) {
        if(!postRepository.existsById(postId)){
            throw new AppException(ErrorCode.POST_NOT_FOUND);
        }

        UUID currentUserId = null;
        try {
            currentUserId = SecurityUtils.getCurrentUserId();
        } catch (Exception ignored) {}

        List<Comment> allComments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);

        Map<UUID, CommentResponse> mapDto = new HashMap<>();
        List<CommentResponse> responseDto = new ArrayList<>();

        for(Comment comment : allComments){
            CommentResponse dto = commentMapper.toResponse(comment, currentUserId);
            mapDto.put(comment.getId(), dto);
        }
        for(Comment comment : allComments){
            CommentResponse dto = mapDto.get(comment.getId());
            if(comment.getParent()!=null){
                CommentResponse parentDto = mapDto.get(comment.getParent().getId());
                parentDto.replies().add(dto);
            } else {
                responseDto.add(dto);
            }
        }

        return responseDto;
    }

    @Override
    @Transactional
    public CommentResponse updateComment(UUID id, UpdateCommentRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
        // Chỉ tác giả mới được sửa comment
        if(!comment.getAuthor().getId().equals(currentUserId)){
            throw new AppException(ErrorCode.NOT_COMMENT_AUTHOR);
        }

        commentMapper.updateComment(request,comment);

        return commentMapper.toResponse(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public void deleteComment(UUID id) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        // Tác giả bình luận, chủ bài viết, hoặc Admin hệ thống có quyền xóa
        boolean isAuthor = comment.getAuthor().getId().equals(currentUserId);
        boolean isPostAuthor = comment.getPost().getAuthor().getId().equals(currentUserId);
        User currentUser = userService.getUserById(currentUserId);
        boolean isSystemAdmin = currentUser.getRole() != null && "ADMIN".equalsIgnoreCase(currentUser.getRole().getName());

        if (!isAuthor && !isPostAuthor && !isSystemAdmin) {
            throw new AppException(ErrorCode.NOT_COMMENT_AUTHOR);
        }

        comment.setDeletedAt(Instant.now());
    }
}
