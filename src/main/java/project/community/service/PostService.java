package project.community.service;

import project.community.dto.post.request.CreatePostRequest;
import project.community.dto.post.request.UpdatePostRequest;
import project.community.dto.post.response.PostResponse;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponse createPost(CreatePostRequest request);
    PostResponse getPostById(UUID id);
    List<PostResponse> getPostsByCommunity(UUID communityId);
    List<PostResponse> getPostsByAuthor(UUID authorId);
    List<PostResponse> getAllPosts();
    PostResponse updatePost(UUID id, UpdatePostRequest request);
    void deletePost(UUID id);
}
