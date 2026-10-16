package project.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.community.dto.tag.response.TagResponse;
import project.community.service.TagService;
import project.shared.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ApiResponse<List<TagResponse>> getAllTags() {
        List<TagResponse> response = tagService.getAllTags();
        return ApiResponse.success(response, "Lấy danh sách hashtag môn học thành công");
    }
}
