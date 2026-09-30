package project.community.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.community.dto.comment.request.CreateCommentRequest;
import project.community.dto.comment.request.UpdateCommentRequest;
import project.community.dto.comment.response.CommentResponse;
import project.community.service.CommentService;
import project.shared.response.ApiResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Tạo bình luận mới
    @PostMapping
    public ApiResponse<CommentResponse> createComment(@Valid @RequestBody CreateCommentRequest request) {
        CommentResponse response = commentService.createComment(request);
        return ApiResponse.success(response, "Đăng bình luận thành công");
    }

    // Lấy danh sách bình luận theo ID bài viết
    @GetMapping("/post/{postId}")
    public ApiResponse<List<CommentResponse>> getCommentsByPostId(@PathVariable UUID postId) {
        List<CommentResponse> response = commentService.getCommentsByPostId(postId);
        return ApiResponse.success(response, "Lấy danh sách bình luận thành công");
    }

    // Cập nhật bình luận
    @PutMapping("/{id}")
    public ApiResponse<CommentResponse> updateComment(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCommentRequest request
    ) {
        CommentResponse response = commentService.updateComment(id, request);
        return ApiResponse.success(response, "Cập nhật bình luận thành công");
    }

    // Xóa bình luận
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteComment(@PathVariable UUID id) {
        commentService.deleteComment(id);
        return ApiResponse.success(null, "Xóa bình luận thành công");
    }
}
