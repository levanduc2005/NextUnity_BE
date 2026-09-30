package project.course.dto.flashcard.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record ReorderFlashcardsRequest(
        @NotEmpty(message = "Danh sách ID flashcard không được để trống")
        List<UUID> flashcardIds
) {}
