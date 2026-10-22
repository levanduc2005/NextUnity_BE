package project.course.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.course.dto.course.request.CreateCourseRequest;
import project.course.dto.course.request.UpdateCourseRequest;
import project.course.dto.course.response.CourseFiltersResponse;
import project.course.dto.course.response.CourseDetailResponse;
import project.course.dto.course.response.CourseSummaryResponse;
import project.course.entity.enums.CourseCategory;
import project.course.entity.enums.CourseDifficulty;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    CourseFiltersResponse getFilterOptions();

    CourseSummaryResponse createCourse(CreateCourseRequest request);

    CourseDetailResponse getCourseById(UUID id);

    Page<CourseSummaryResponse> searchCourses(CourseCategory category, CourseDifficulty difficulty, String search, Pageable pageable);

    List<CourseSummaryResponse> getPopularCourses(int limit);

    List<CourseSummaryResponse> getMyCreatedCourses();

    CourseSummaryResponse updateCourse(UUID id, UpdateCourseRequest request);

    void deleteCourse(UUID id);
}
