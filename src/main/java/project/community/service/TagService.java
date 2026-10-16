package project.community.service;

import project.community.dto.tag.response.TagResponse;

import java.util.List;

public interface TagService {
    List<TagResponse> getAllTags();
}
