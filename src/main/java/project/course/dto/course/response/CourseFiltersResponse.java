package project.course.dto.course.response;

import java.util.List;

public record CourseFiltersResponse(
        List<CourseCategoryResponse> categories,
        List<CourseDifficultyResponse> difficulties
) {}
