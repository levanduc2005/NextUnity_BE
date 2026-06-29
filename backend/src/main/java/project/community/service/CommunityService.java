package project.community.service;

import project.community.dto.community.request.CreateCommunityRequest;
import project.community.dto.community.request.UpdateCommunityRequest;
import project.community.dto.community.response.CommunityMemberResponse;
import project.community.dto.community.response.CommunityResponse;

import java.util.List;
import java.util.UUID;

public interface CommunityService {
    CommunityResponse createCommunity(CreateCommunityRequest request);
    CommunityResponse getCommunityById(UUID communityId);
    List<CommunityResponse> getAllCommunities();
    CommunityResponse updateCommunity(UUID communityId, UpdateCommunityRequest request);
    void deleteCommunity(UUID communityId);
    void joinCommunity(UUID communityId);
    void leaveCommunity(UUID communityId);
    List<CommunityMemberResponse> getCommunityMembers(UUID communityId);
}
