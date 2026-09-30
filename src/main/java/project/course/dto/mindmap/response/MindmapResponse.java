package project.course.dto.mindmap.response;

import java.time.Instant;
import java.util.UUID;

public record MindmapResponse(
        UUID id,
        UUID lessonId,
        String title,
        String data,
        Instant createdAt,
        Instant updatedAt
) {}
