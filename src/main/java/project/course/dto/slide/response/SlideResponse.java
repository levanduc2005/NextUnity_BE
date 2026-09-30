package project.course.dto.slide.response;

import project.course.entity.enums.SlideStatus;

import java.time.Instant;
import java.util.UUID;

public record SlideResponse(
        UUID id,
        UUID lessonId,
        UUID uploaderId,
        String uploaderName,
        String fileName,
        String fileUrl,
        SlideStatus status,
        String rejectionReason,
        Instant createdAt,
        Instant updatedAt
) {}
