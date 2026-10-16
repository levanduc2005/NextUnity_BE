package project.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.course.dto.course.request.CreateCourseRequest;
import project.course.dto.course.request.UpdateCourseRequest;
import project.course.dto.course.response.CourseCategoryResponse;
import project.course.dto.course.response.CourseDifficultyResponse;
import project.course.dto.course.response.CourseFiltersResponse;
import project.course.dto.course.response.CourseDetailResponse;
import project.course.dto.course.response.CourseSummaryResponse;
import project.course.entity.Course;
import project.course.entity.Enrollment;
import project.course.entity.enums.CourseCategory;
import project.course.entity.enums.CourseDifficulty;
import project.course.mapper.CourseMapper;
import project.course.repository.CourseRepository;
import project.course.repository.EnrollmentRepository;
import project.course.service.CourseService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.entity.User;
import project.user.service.UserService;

import project.course.entity.enums.LessonProgressStatus;
import project.course.repository.LessonProgressRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final UserService userService;

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LessonProgressRepository lessonProgressRepository;

    private final CourseMapper courseMapper;

    @Override
    public CourseFiltersResponse getFilterOptions() {
        return new CourseFiltersResponse(getCategories(), getDifficulties());
    }

    private List<CourseCategoryResponse> getCategories() {
        return Arrays.stream(CourseCategory.values())
                .map(category -> new CourseCategoryResponse(category.name(), category.getDisplayName()))
                .toList();
    }

    private List<CourseDifficultyResponse> getDifficulties() {
        return Arrays.stream(CourseDifficulty.values())
                .map(difficulty -> new CourseDifficultyResponse(difficulty.name(), difficulty.getDisplayName()))
                .toList();
    }

    @Override
    @Transactional
    public CourseSummaryResponse createCourse(CreateCourseRequest request) {
        User currentUser = userService.getUserById(SecurityUtils.getCurrentUserId());

        Course course = courseMapper.toEntity(request);
        course.setCreator(currentUser);
        course.setTotalLessons(0);

        return courseMapper.toSummaryResponse(courseRepository.save(course));
    }

    @Override
    public CourseDetailResponse getCourseById(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        Optional<UUID> currentUserIdOpt = SecurityUtils.getOptionalCurrentUserId();
        Optional<Enrollment> enrollmentOpt = currentUserIdOpt
                .flatMap(userId -> enrollmentRepository.findByUserIdAndCourseId(userId, id));

        boolean enrolled = enrollmentOpt.isPresent();
        int myProgress = enrollmentOpt.map(Enrollment::getProgressPercent).orElse(0);

        Set<UUID> completedLessonIds = enrolled && currentUserIdOpt.isPresent()
                ? lessonProgressRepository.findByUserIdAndLesson_Module_Course_Id(currentUserIdOpt.get(), id).stream()
                        .filter(lp -> lp.getStatus() == LessonProgressStatus.COMPLETED)
                        .map(lp -> lp.getLesson().getId())
                        .collect(Collectors.toSet())
                : Set.of();

        return courseMapper.toDetailResponse(course, enrolled, myProgress, completedLessonIds);
    }

    @Override
    public Page<CourseSummaryResponse> searchCourses(CourseCategory category, CourseDifficulty difficulty, String search,
            Pageable pageable) {
        Page<Course> courses = courseRepository.searchCourses(category, difficulty, search, pageable);

        return courses.map(courseMapper::toSummaryResponse);
    }

    @Override
    public List<CourseSummaryResponse> getMyCreatedCourses() {
        UUID currentUserId = SecurityUtils.getCurrentUserId();
        List<Course> courses = courseRepository.findByCreatorIdOrderByCreatedAtDesc(currentUserId);

        return courses.stream()
                .map(courseMapper::toSummaryResponse)
                .toList();
    }

    @Override
    @Transactional
    public CourseSummaryResponse updateCourse(UUID id, UpdateCourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        courseMapper.updateEntity(request, course);
        return courseMapper.toSummaryResponse(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void deleteCourse(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        courseRepository.delete(course);
    }
}
