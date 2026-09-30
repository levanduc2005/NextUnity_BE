package project.community.service;

import project.community.dto.reaction.request.ReactionRequest;
import project.community.dto.reaction.response.ReactionResponse;

import java.util.UUID;

public interface ReactionService {

    ReactionResponse reactPost(UUID postId, ReactionRequest request);

    ReactionResponse reactComment(UUID commentId, ReactionRequest request);
}
