package project.community.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.community.dto.post.request.CreatePostRequest;
import project.community.dto.post.request.UpdatePostRequest;
import project.community.dto.post.response.PostResponse;
import project.community.entity.Post;
import project.community.entity.Tag;
import project.community.mapper.PostMapper;
import project.community.repository.PostRepository;
import project.community.repository.TagRepository;
import project.community.service.PostService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.entity.User;
import project.user.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final UserService userService;

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    private final PostMapper postMapper;

    @Override
    @Transactional
    @CacheEvict(value = "popularPosts", allEntries = true)
    public PostResponse createPost(CreatePostRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        User author = userService.getUserById(userId);

        // Tạo bài viết mới
        Post post = postMapper.toEntity(request);
        post.setAuthor(author);
        post.setTags(handleTags(request.tagNames()));

        return postMapper.toResponse(postRepository.save(post), userId);
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostById(UUID postId) {
        UUID currentUserId = SecurityUtils.getOptionalCurrentUserId().orElse(null);
        return postRepository.findById(postId)
                .map(p -> postMapper.toResponse(p, currentUserId))
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    public List<PostResponse> getPostsByAuthor(UUID authorId) {
        // Kiểm tra xem người dùng có tồn tại hay không
        if(!userService.existsById(authorId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        UUID currentUserId = SecurityUtils.getOptionalCurrentUserId().orElse(null);
        return postRepository.findByAuthorIdOrderByCreatedAtDesc(authorId).stream()
                .map(p -> postMapper.toResponse(p, currentUserId))
                .toList();
    }

    @Override
    public List<PostResponse> getAllPosts() {
        UUID currentUserId = SecurityUtils.getOptionalCurrentUserId().orElse(null);
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(p -> postMapper.toResponse(p, currentUserId))
                .toList();
    }

    @Override
    @Cacheable(value = "popularPosts", key = "#limit")
    public List<PostResponse> getPopularPosts(int limit) {
        UUID currentUserId = SecurityUtils.getOptionalCurrentUserId().orElse(null);
        Pageable pageable = PageRequest.of(0, Math.max(1, limit));
        return postRepository.findTrendingPosts(pageable).stream()
                .map(p -> postMapper.toResponse(p, currentUserId))
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = "popularPosts", allEntries = true)
    public PostResponse updatePost(UUID id, UpdatePostRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        // Chỉ tác giả mới được sửa bài viết
        if(!post.getAuthor().getId().equals(userId)) {
            throw new AppException(ErrorCode.NOT_POST_AUTHOR);
        }

        postMapper.updatePost(request, post);
        post.setTags(handleTags(request.tagNames()));

        return postMapper.toResponse(postRepository.save(post), userId);
    }

    @Override
    @Transactional
    @CacheEvict(value = "popularPosts", allEntries = true)
    public void deletePost(UUID id) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        boolean isAuthor = post.getAuthor().getId().equals(currentUserId);
        User currentUser = userService.getUserById(currentUserId);
        boolean isSystemAdmin = currentUser.getRole() != null && "ADMIN".equalsIgnoreCase(currentUser.getRole().getName());

        if(!isAuthor && !isSystemAdmin) {
            throw new AppException(ErrorCode.NOT_POST_AUTHOR);
        }

        postRepository.delete(post);
    }

    private Set<Tag> handleTags(Set<String> tagNames) {
        Set<Tag> tags = new HashSet<>();
        if (tagNames == null) return tags;
        for (String name : tagNames) {
            String cleanName = name.trim().toLowerCase();
            if (cleanName.isEmpty()) continue;
            Tag tag = tagRepository.findByName(cleanName)
                    .orElseGet(() -> {
                        Tag newTag = new Tag();
                        newTag.setName(cleanName);
                        return tagRepository.save(newTag);
                    });
            tags.add(tag);
        }
        return tags;
    }
}
