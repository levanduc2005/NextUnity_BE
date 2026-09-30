package project.course.dto.flashcard.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateFlashcardRequest(
        @NotBlank(message = "Mặt trước không được để trống")
        String front,

        @NotBlank(message = "Mặt sau không được để trống")
        String back
) {}
