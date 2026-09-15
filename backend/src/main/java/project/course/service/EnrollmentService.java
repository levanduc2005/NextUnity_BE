package project.course.service;

import project.course.dto.enrollment.response.EnrollmentResponse;
import project.course.dto.progress.request.UpdateProgressRequest;
import project.course.dto.progress.response.LessonProgressResponse;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {

    EnrollmentResponse enrollCourse(UUID courseId);

    List<EnrollmentResponse> getMyEnrollments();

    EnrollmentResponse getMyEnrollmentByCourseId(UUID courseId);

    LessonProgressResponse updateLessonProgress(UUID lessonId, UpdateProgressRequest request);

    List<LessonProgressResponse> getMyProgressByCourseId(UUID courseId);
}
