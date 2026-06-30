package project.community.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.community.dto.reaction.request.ReactionRequest;
import project.community.dto.reaction.response.ReactionResponse;
import project.community.entity.Comment;
import project.community.entity.Post;
import project.community.entity.Reaction;
import project.community.entity.enums.ReactionType;
import project.community.mapper.ReactionMapper;
import project.community.repository.CommentRepository;
import project.community.repository.PostRepository;
import project.community.repository.ReactionRepository;
import project.community.service.ReactionService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.entity.User;
import project.user.service.UserService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final UserService userService;

    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final ReactionMapper reactionMapper;

    @Override
    @Transactional
    public ReactionResponse reactPost(UUID postId, ReactionRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        Optional<Reaction> existingReaction = reactionRepository.findByPost_IdAndUser_Id(postId, currentUserId);
        ReactionType finalReaction = request.reactionType();

        if (post.getReactions() == null) {
            post.setReactions(new ArrayList<>());
        }

        if(existingReaction.isPresent()) {
            Reaction reaction = existingReaction.get();
            if(reaction.getReactionType().equals(finalReaction)) {
                // Nếu reaction hiện tại giống với reaction mới, xóa reaction
                reactionRepository.delete(reaction);
                post.getReactions().remove(reaction);
                finalReaction = null;
            } else {
                // Nếu reaction hiện tại khác với reaction mới, cập nhật reaction
                reaction.setReactionType(finalReaction);
            }
        }else{ // Nếu chưa có reaction, tạo mới
            User user = userService.getUserById(currentUserId);
            Reaction newReaction = new Reaction();
            newReaction.setPost(post);
            newReaction.setUser(user);
            newReaction.setReactionType(finalReaction);

            post.getReactions().add(newReaction);
        }
        return reactionMapper.toPostResponse(post, finalReaction);
    }

    @Override
    @Transactional
    public ReactionResponse reactComment(UUID commentId, ReactionRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        Optional<Reaction> existingReaction = reactionRepository.findByComment_IdAndUser_Id(commentId, currentUserId);
        ReactionType finalReaction = request.reactionType();

        if (comment.getReactions() == null) {
            comment.setReactions(new ArrayList<>());
        }

        if(existingReaction.isPresent()) {
            Reaction reaction = existingReaction.get();
            if(reaction.getReactionType().equals(finalReaction)) {
                // Nếu reaction hiện tại giống với reaction mới, xóa reaction
                reactionRepository.delete(reaction);

                comment.getReactions().remove(reaction);

                finalReaction = null;
            } else {
                // Nếu reaction hiện tại khác với reaction mới, cập nhật reaction
                reaction.setReactionType(finalReaction);
            }
        }else{ // Nếu chưa có reaction, tạo mới
            User user = userService.getUserById(currentUserId);
            Reaction newReaction = new Reaction();
            newReaction.setComment(comment);
            newReaction.setUser(user);
            newReaction.setReactionType(finalReaction);

            comment.getReactions().add(newReaction);
        }
        return reactionMapper.toCommentResponse(comment, finalReaction);
    }
}
