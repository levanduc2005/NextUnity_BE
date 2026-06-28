package project.community.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.community.dto.community.request.CreateCommunityRequest;
import project.community.dto.community.request.UpdateCommunityRequest;
import project.community.dto.community.response.CommunityMemberResponse;
import project.community.dto.community.response.CommunityResponse;
import project.community.entity.Community;
import project.community.entity.CommunityMember;
import project.community.entity.enums.CommunityRole;
import project.community.mapper.CommunityMapper;
import project.community.repository.CommunityMemberRepository;
import project.community.repository.CommunityRepository;
import project.community.service.CommunityService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.entity.User;
import project.user.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final UserService userService;

    private final CommunityRepository communityRepository;
    private final CommunityMemberRepository communityMemberRepository;

    private final CommunityMapper communityMapper;

    @Override
    @Transactional
    public CommunityResponse createCommunity(CreateCommunityRequest request) {
        // Kiểm tra xem tên cộng đồng đã tồn tại chưa
        if (communityRepository.existsByName(request.name())) {
            throw new AppException(ErrorCode.COMMUNITY_NAME_ALREADY_EXISTS);
        }
        UUID creatorId = SecurityUtils.getCurrentUserId();
        User creator = userService.getUserById(creatorId);
        // Tạo cộng đồng mới
        Community community = communityMapper.toEntity(request);
        community.setCreator(creator);

        Community savedCommunity = communityRepository.save(community);
        // Tạo CommunityMember cho creator với vai trò ADMIN
        CommunityMember member = new CommunityMember();
        member.setCommunity(savedCommunity);
        member.setUser(creator);
        member.setRole(CommunityRole.ADMIN);
        communityMemberRepository.save(member);

        return communityMapper.toResponse(savedCommunity);
    }
    @Override
    @Transactional(readOnly = true)
    public CommunityResponse getCommunityById(UUID id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMUNITY_NOT_FOUND));
        return communityMapper.toResponse(community);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommunityResponse> getAllCommunities() {
        return communityRepository.findAll().stream()
                .map(communityMapper::toResponse)
                .toList();
    }

    @Override
    public CommunityResponse updateCommunity(UUID id, UpdateCommunityRequest requestl) {
        return null;
    }

    @Override
    public void deleteCommunity(UUID id) {

    }

    @Override
    public void joinCommunity(UUID id) {

    }

    @Override
    public void leaveCommunity(UUID id) {

    }

    @Override
    public List<CommunityMemberResponse> getCommunityMembers(UUID id) {
        return List.of();
    }
}
