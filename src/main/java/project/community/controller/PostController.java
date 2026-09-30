package project.community.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.community.dto.post.request.CreatePostRequest;
import project.community.dto.post.request.UpdatePostRequest;
import project.community.dto.post.response.PostResponse;
import project.community.service.PostService;
import project.shared.response.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<PostResponse> createPost(@Valid @RequestBody CreatePostRequest request) {
        PostResponse response = postService.createPost(request);
        return ApiResponse.success(response, "Đăng bài viết thành công");
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPostById(@PathVariable UUID id) {
        PostResponse response = postService.getPostById(id);
        return ApiResponse.success(response, "Lấy chi tiết bài viết thành công");
    }

    @GetMapping("/community/{communityId}")
    public ApiResponse<List<PostResponse>> getPostsByCommunity(@PathVariable UUID communityId) {
        List<PostResponse> response = postService.getPostsByCommunity(communityId);
        return ApiResponse.success(response, "Lấy danh sách bài viết của cộng đồng thành công");
    }

    @GetMapping("/author/{authorId}")
    public ApiResponse<List<PostResponse>> getPostsByAuthor(@PathVariable UUID authorId) {
        List<PostResponse> response = postService.getPostsByAuthor(authorId);
        return ApiResponse.success(response, "Lấy danh sách bài viết của tác giả thành công");
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> getAllPosts() {
        List<PostResponse> response = postService.getAllPosts();
        return ApiResponse.success(response, "Lấy danh sách bài viết hệ thống thành công");
    }

    @PutMapping("/{id}")
    public ApiResponse<PostResponse> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequest request
    ) {
        PostResponse response = postService.updatePost(id, request);
        return ApiResponse.success(response, "Cập nhật bài viết thành công");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ApiResponse.success(null, "Xóa bài viết thành công");
    }
}
