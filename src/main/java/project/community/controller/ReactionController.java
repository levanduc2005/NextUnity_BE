package project.community.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.community.dto.reaction.request.ReactionRequest;
import project.community.dto.reaction.response.ReactionResponse;
import project.community.service.ReactionService;
import project.shared.response.ApiResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api/reactions")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping("/post/{postId}")
    public ApiResponse<ReactionResponse> reactPost(
            @PathVariable UUID postId,
            @Valid @RequestBody ReactionRequest request
    ) {
        ReactionResponse response = reactionService.reactPost(postId, request);
        return ApiResponse.success(response, "Bày tỏ cảm xúc bài viết thành công");
    }

    @PostMapping("/comment/{commentId}")
    public ApiResponse<ReactionResponse> reactComment(
            @PathVariable UUID commentId,
            @Valid @RequestBody ReactionRequest request
    ) {
        ReactionResponse response = reactionService.reactComment(commentId, request);
        return ApiResponse.success(response, "Bày tỏ cảm xúc bình luận thành công");
    }
}