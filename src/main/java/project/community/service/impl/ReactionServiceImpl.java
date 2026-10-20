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
        String lockKey = ("post-reaction:" + currentUserId + ":" + postId).intern();

        synchronized (lockKey) {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

            if (post.getReactions() == null) {
                post.setReactions(new ArrayList<>());
            }

            ReactionType finalReaction = request.reactionType();

            // 1. Kiểm tra trong danh sách in-memory của post trước
            Reaction existingReaction = null;
            for (Reaction r : post.getReactions()) {
                if (r.getUser() != null && currentUserId.equals(r.getUser().getId())) {
                    existingReaction = r;
                    break;
                }
            }

            // 2. Nếu chưa thấy trong collection, kiểm tra trực tiếp DB
            if (existingReaction == null) {
                existingReaction = reactionRepository.findByPost_IdAndUser_Id(postId, currentUserId).orElse(null);
            }

            if (existingReaction != null) {
                final Reaction reaction = existingReaction;
                if (reaction.getReactionType().equals(finalReaction)) {
                    // Nếu reaction hiện tại giống với reaction mới -> Xóa reaction
                    post.getReactions().removeIf(r -> r.getId().equals(reaction.getId())
                            || (r.getUser() != null && currentUserId.equals(r.getUser().getId())));
                    reactionRepository.delete(reaction);
                    finalReaction = null;
                } else {
                    // Nếu reaction hiện tại khác với reaction mới -> Cập nhật reaction trực tiếp trong managed context
                    reaction.setReactionType(finalReaction);
                    boolean found = false;
                    for (Reaction r : post.getReactions()) {
                        if (r.getId().equals(reaction.getId())
                                || (r.getUser() != null && currentUserId.equals(r.getUser().getId()))) {
                            r.setReactionType(finalReaction);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        post.getReactions().add(reaction);
                    }
                }
            } else {
                // Nếu hoàn toàn chưa có reaction -> Tạo mới
                User user = userService.getUserById(currentUserId);
                Reaction newReaction = new Reaction();
                newReaction.setPost(post);
                newReaction.setUser(user);
                newReaction.setReactionType(finalReaction);

                Reaction saved = reactionRepository.save(newReaction);
                post.getReactions().add(saved);
            }

            return reactionMapper.toPostResponse(post, finalReaction);
        }
    }

    @Override
    @Transactional
    public ReactionResponse reactComment(UUID commentId, ReactionRequest request) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        String lockKey = ("comment-reaction:" + currentUserId + ":" + commentId).intern();

        synchronized (lockKey) {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

            if (comment.getReactions() == null) {
                comment.setReactions(new ArrayList<>());
            }

            ReactionType finalReaction = request.reactionType();

            // 1. Kiểm tra trong danh sách in-memory của comment trước
            Reaction existingReaction = null;
            for (Reaction r : comment.getReactions()) {
                if (r.getUser() != null && currentUserId.equals(r.getUser().getId())) {
                    existingReaction = r;
                    break;
                }
            }

            // 2. Nếu chưa thấy trong collection, kiểm tra trực tiếp DB
            if (existingReaction == null) {
                existingReaction = reactionRepository.findByComment_IdAndUser_Id(commentId, currentUserId).orElse(null);
            }

            if (existingReaction != null) {
                final Reaction reaction = existingReaction;
                if (reaction.getReactionType().equals(finalReaction)) {
                    // Nếu reaction hiện tại giống với reaction mới -> Xóa reaction
                    comment.getReactions().removeIf(r -> r.getId().equals(reaction.getId())
                            || (r.getUser() != null && currentUserId.equals(r.getUser().getId())));
                    reactionRepository.delete(reaction);
                    finalReaction = null;
                } else {
                    // Nếu reaction hiện tại khác với reaction mới -> Cập nhật reaction trực tiếp trong managed context
                    reaction.setReactionType(finalReaction);
                    boolean found = false;
                    for (Reaction r : comment.getReactions()) {
                        if (r.getId().equals(reaction.getId())
                                || (r.getUser() != null && currentUserId.equals(r.getUser().getId()))) {
                            r.setReactionType(finalReaction);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        comment.getReactions().add(reaction);
                    }
                }
            } else {
                // Nếu hoàn toàn chưa có reaction -> Tạo mới
                User user = userService.getUserById(currentUserId);
                Reaction newReaction = new Reaction();
                newReaction.setComment(comment);
                newReaction.setUser(user);
                newReaction.setReactionType(finalReaction);

                Reaction saved = reactionRepository.save(newReaction);
                comment.getReactions().add(saved);
            }

            return reactionMapper.toCommentResponse(comment, finalReaction);
        }
    }
}
