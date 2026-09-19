package project.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.course.dto.course.request.CreateCourseRequest;
import project.course.dto.course.request.UpdateCourseRequest;
import project.course.dto.course.response.CourseDetailResponse;
import project.course.dto.course.response.CourseSummaryResponse;
import project.course.entity.Course;
import project.course.entity.Enrollment;
import project.course.entity.enums.CourseDifficulty;
import project.course.mapper.CourseMapper;
import project.course.repository.CourseRepository;
import project.course.repository.EnrollmentRepository;
import project.course.service.CourseService;
import project.course.validator.CourseAccessValidator;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.entity.User;
import project.user.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final UserService userService;

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    private final CourseMapper courseMapper;

    private final CourseAccessValidator courseAccessValidator;

    @Override
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

        UUID currentUserId = SecurityUtils.getCurrentUserId();
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findByUserIdAndCourseId(currentUserId, id);

        boolean enrolled = enrollmentOpt.isPresent();
        int myProgress = enrollmentOpt.map(Enrollment::getProgressPercent).orElse(0);

        return courseMapper.toDetailResponse(course, enrolled, myProgress);
    }

    @Override
    public Page<CourseSummaryResponse> searchCourses(String category, CourseDifficulty difficulty, String search, Pageable pageable) {
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
    public CourseSummaryResponse updateCourse(UUID id, UpdateCourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        courseAccessValidator.validateCreatorOrAdmin(course);

        courseMapper.updateEntity(request, course);
        return courseMapper.toSummaryResponse(courseRepository.save(course));
    }

    @Override
    public void deleteCourse(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        courseAccessValidator.validateCreatorOrAdmin(course);

        courseRepository.delete(course);
    }
}
