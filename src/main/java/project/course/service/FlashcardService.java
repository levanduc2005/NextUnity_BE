package project.course.service;

import project.course.dto.flashcard.request.CreateFlashcardRequest;
import project.course.dto.flashcard.request.UpdateFlashcardRequest;
import project.course.dto.flashcard.response.FlashcardResponse;

import project.course.dto.flashcard.request.ReorderFlashcardsRequest;

import java.util.List;
import java.util.UUID;

public interface FlashcardService {

    List<FlashcardResponse> getFlashcardsByLessonId(UUID lessonId);

    FlashcardResponse createFlashcard(UUID lessonId, CreateFlashcardRequest request);

    FlashcardResponse updateFlashcard(UUID id, UpdateFlashcardRequest request);

    void reorderFlashcards(UUID lessonId, ReorderFlashcardsRequest request);

    void deleteFlashcard(UUID id);
}
