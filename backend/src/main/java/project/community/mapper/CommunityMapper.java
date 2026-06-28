package project.community.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.community.dto.community.request.CreateCommunityRequest;
import project.community.dto.community.response.CommunityMemberResponse;
import project.community.dto.community.response.CommunityResponse;
import project.community.entity.Community;
import project.community.entity.CommunityMember;

@Mapper(componentModel = "spring")
public interface CommunityMapper {

    Community toEntity(CreateCommunityRequest response);

    @Mapping(target = "creatorId", source = "creator.id")
    @Mapping(target = "creatorName", source = "creator.username")
    @Mapping(
            target = "memberCount",
            expression = "java(community.getMembers() != null ? community.getMembers().size() : 0)"
    )
    CommunityResponse toResponse(Community community);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "joinedAt", source = "createdAt")
    CommunityMemberResponse toMemberResponse(CommunityMember member);
}
