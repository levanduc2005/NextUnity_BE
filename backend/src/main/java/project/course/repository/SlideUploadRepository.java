package project.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.course.entity.SlideUpload;

import project.course.entity.enums.SlideStatus;

import java.util.List;
import java.util.UUID;

public interface SlideUploadRepository extends JpaRepository<SlideUpload, UUID> {
    List<SlideUpload> findByLessonId(UUID lessonId);
    List<SlideUpload> findByLessonIdAndStatus(UUID lessonId, SlideStatus status);
    List<SlideUpload> findByLessonIdAndIsOfficialTrue(UUID lessonId);
    List<SlideUpload> findByUploaderId(UUID uploaderId);
}
