package project.community.service;

import project.community.dto.community.request.CreateCommunityRequest;
import project.community.dto.community.request.UpdateCommunityRequest;
import project.community.dto.community.response.CommunityMemberResponse;
import project.community.dto.community.response.CommunityResponse;

import java.util.List;
import java.util.UUID;

public interface CommunityService {
    CommunityResponse createCommunity(CreateCommunityRequest request);
    CommunityResponse getCommunityById(UUID id);
    List<CommunityResponse> getAllCommunities();
    CommunityResponse updateCommunity(UUID id, UpdateCommunityRequest request);
    void deleteCommunity(UUID id);
    void joinCommunity(UUID id);
    void leaveCommunity(UUID id);
    List<CommunityMemberResponse> getCommunityMembers(UUID id);
}
