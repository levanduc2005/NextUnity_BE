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
    public CommunityResponse getCommunityById(UUID communityId) {
        Community community = communityRepository.findById(communityId)
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
    @Transactional
    public CommunityResponse updateCommunity(UUID communityId, UpdateCommunityRequest requestl) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMUNITY_NOT_FOUND));
        // Kiểm tra xem người dùng hiện tại có phải là creator của cộng đồng không
        if(!community.getCreator().getId().equals(currentUserId)) {
            throw new AppException(ErrorCode.NOT_COMMUNITY_CREATOR);
        }
        communityMapper.updateCommunity(requestl, community);

        return communityMapper.toResponse(community);
    }

    @Override
    @Transactional
    public void deleteCommunity(UUID communityId) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMUNITY_NOT_FOUND));
        // Kiểm tra xem người dùng hiện tại có phải là creator của cộng đồng không
        if(!community.getCreator().getId().equals(currentUserId)) {
            throw new AppException(ErrorCode.NOT_COMMUNITY_CREATOR);
        }
        communityRepository.delete(community);
    }

    @Override
    @Transactional
    public void joinCommunity(UUID communityId) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        User user = userService.getUserById(currentUserId);

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMUNITY_NOT_FOUND));
        // Kiểm tra xem người dùng đã tham gia cộng đồng chưa
        if(communityMemberRepository.existsByCommunity_IdAndUser_Id(community.getId(), currentUserId)) {
            throw new AppException(ErrorCode.ALREADY_JOINED_COMMUNITY);
        }
        // Tạo CommunityMember cho user với vai trò MEMBER
        CommunityMember member = new CommunityMember();
        member.setCommunity(community);
        member.setUser(user);
        member.setRole(CommunityRole.MEMBER);

        communityMemberRepository.save(member);
    }

    @Override
    @Transactional
    public void leaveCommunity(UUID communityId) {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        User user = userService.getUserById(currentUserId);

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMUNITY_NOT_FOUND));
        // Người tạo cộng đồng không thể rời khỏi cộng đồng
        if(community.getCreator().getId().equals(currentUserId)) {
            throw new AppException(ErrorCode.CANNOT_LEAVE_AS_CREATOR);
        }
        CommunityMember member = communityMemberRepository
                .findByCommunity_IdAndUser_Id(community.getId(), currentUserId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_A_MEMBER));

        communityMemberRepository.delete(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommunityMemberResponse> getCommunityMembers(UUID communityId) {
        if(!communityRepository.existsById(communityId)) {
            throw new AppException(ErrorCode.COMMUNITY_NOT_FOUND);
        }
        return communityMemberRepository.findByCommunity_Id(communityId).stream()
                .map(communityMapper::toMemberResponse)
                .toList();
    }
}
