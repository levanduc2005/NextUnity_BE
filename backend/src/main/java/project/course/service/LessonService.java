package project.course.service;

import project.course.dto.lesson.request.CreateLessonRequest;
import project.course.dto.lesson.request.UpdateLessonRequest;
import project.course.dto.lesson.response.LessonDetailResponse;

import java.util.UUID;

public interface LessonService {

    LessonDetailResponse createLesson(UUID moduleId, CreateLessonRequest request);

    LessonDetailResponse getLessonById(UUID lessonId);

    LessonDetailResponse updateLesson(UUID lessonId, UpdateLessonRequest request);

    void deleteLesson(UUID lessonId);
}
