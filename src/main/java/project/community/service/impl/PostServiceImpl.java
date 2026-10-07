package project.community.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.community.dto.post.request.CreatePostRequest;
import project.community.dto.post.request.UpdatePostRequest;
import project.community.dto.post.response.PostResponse;
import project.community.entity.Community;
import project.community.entity.Post;
import project.community.entity.Tag;
import project.community.mapper.PostMapper;
import project.community.repository.CommunityMemberRepository;
import project.community.repository.CommunityRepository;
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
    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final TagRepository tagRepository;

    private final PostMapper postMapper;

    @Override
    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();

        Community community = communityRepository.findById(request.communityId())
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        // Kiểm tra xem người dùng có phải là thành viên của cộng đồng hay không
        if(!communityMemberRepository.existsByCommunity_IdAndUser_Id(community.getId(), userId)) {
            throw new AppException(ErrorCode.NOT_A_MEMBER);
        }

        User author = userService.getUserById(userId);

        // Tạo bài viết mới
        Post post = postMapper.toEntity(request);
        post.setAuthor(author);
        post.setCommunity(community);
        post.setTags(handleTags(request.tagNames()));

        return postMapper.toResponse(postRepository.save(post));
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPostById(UUID postId) {
        return postRepository.findById(postId)
                .map(postMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByCommunity(UUID communityId) {
        // Kiểm tra xem cộng đồng có tồn tại hay không
        if(!communityRepository.existsById(communityId)) {
            throw new AppException(ErrorCode.COMMUNITY_NOT_FOUND);
        }

        return postRepository.findByCommunityIdOrderByCreatedAtDesc(communityId).stream()
                .map(postMapper::toResponse)
                .toList();
    }

    @Override
    public List<PostResponse> getPostsByAuthor(UUID authorId) {
        // Kiểm tra xem người dùng có tồn tại hay không
        if(!userService.existsById(authorId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return postRepository.findByAuthorIdOrderByCreatedAtDesc(authorId).stream()
                .map(postMapper::toResponse)
                .toList();
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
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

        return postMapper.toResponse(postRepository.save(post));
    }

    @Override
    @Transactional
    public void deletePost(UUID id) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));

        // Tác giả bài viết hoặc Admin của Group có quyền xóa bài
        boolean isAuthor = post.getAuthor().getId().equals(currentUserId);
        boolean isAdmin = communityMemberRepository
                .findByCommunity_IdAndUser_Id(post.getCommunity().getId(), currentUserId)
                .map(member ->
                        member.getRole() == project.community.entity.enums.CommunityRole.ADMIN
                        || member.getRole() == project.community.entity.enums.CommunityRole.MODERATOR)
                .orElse(false);

        if(!isAuthor && !isAdmin) {
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
