package project.course.dto.flashcard.response;

import java.time.Instant;
import java.util.UUID;

public record FlashcardResponse(
        UUID id,
        UUID lessonId,
        String front,
        String back,
        Integer orderIndex,
        Instant createdAt,
        Instant updatedAt
) {}
