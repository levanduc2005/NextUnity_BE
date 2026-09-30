package project.course.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.course.dto.flashcard.request.CreateFlashcardRequest;
import project.course.dto.flashcard.request.ReorderFlashcardsRequest;
import project.course.dto.flashcard.request.UpdateFlashcardRequest;
import project.course.dto.flashcard.response.FlashcardResponse;
import project.course.service.FlashcardService;
import project.shared.response.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FlashcardController {

    private final FlashcardService flashcardService;

    @GetMapping("/api/lessons/{lessonId}/flashcards")
    public ApiResponse<List<FlashcardResponse>> getFlashcardsByLessonId(@PathVariable UUID lessonId) {
        List<FlashcardResponse> response = flashcardService.getFlashcardsByLessonId(lessonId);
        return ApiResponse.success(response, "Lấy danh sách flashcard thành công");
    }

    @PostMapping("/api/lessons/{lessonId}/flashcards")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<FlashcardResponse> createFlashcard(
            @PathVariable UUID lessonId,
            @Valid @RequestBody CreateFlashcardRequest request
    ) {
        FlashcardResponse response = flashcardService.createFlashcard(lessonId, request);
        return ApiResponse.success(response, "Tạo flashcard thành công");
    }

    @PutMapping("/api/flashcards/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<FlashcardResponse> updateFlashcard(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateFlashcardRequest request
    ) {
        FlashcardResponse response = flashcardService.updateFlashcard(id, request);
        return ApiResponse.success(response, "Cập nhật flashcard thành công");
    }

    @PutMapping("/api/lessons/{lessonId}/flashcards/reorder")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> reorderFlashcards(
            @PathVariable UUID lessonId,
            @Valid @RequestBody ReorderFlashcardsRequest request
    ) {
        flashcardService.reorderFlashcards(lessonId, request);
        return ApiResponse.success(null, "Sắp xếp lại flashcard thành công");
    }

    @DeleteMapping("/api/flashcards/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteFlashcard(@PathVariable UUID id) {
        flashcardService.deleteFlashcard(id);
        return ApiResponse.success(null, "Xóa flashcard thành công");
    }
}
