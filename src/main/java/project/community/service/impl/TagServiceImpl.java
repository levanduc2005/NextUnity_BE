package project.community.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.community.dto.tag.response.TagResponse;
import project.community.repository.TagRepository;
import project.community.service.TagService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<TagResponse> getAllTags() {
        return tagRepository.findAllByOrderByNameAsc().stream()
                .map(tag -> new TagResponse(tag.getId(), tag.getName(), tag.getDescription()))
                .toList();
    }
}
