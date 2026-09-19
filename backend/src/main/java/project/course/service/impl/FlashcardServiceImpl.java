package project.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.course.dto.flashcard.request.CreateFlashcardRequest;
import project.course.dto.flashcard.request.ReorderFlashcardsRequest;
import project.course.dto.flashcard.request.UpdateFlashcardRequest;
import project.course.dto.flashcard.response.FlashcardResponse;
import project.course.entity.Flashcard;
import project.course.entity.Lesson;
import project.course.mapper.FlashcardMapper;
import project.course.repository.FlashcardRepository;
import project.course.repository.LessonRepository;
import project.course.service.FlashcardService;
import project.course.validator.CourseAccessValidator;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlashcardServiceImpl implements FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final LessonRepository lessonRepository;

    private final FlashcardMapper flashcardMapper;

    private final CourseAccessValidator courseAccessValidator;

    @Override
    public List<FlashcardResponse> getFlashcardsByLessonId(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        courseAccessValidator.validateAccess(lesson.getCourse());

        return flashcardRepository.findByLessonIdOrderByOrderIndexAsc(lessonId)
                .stream()
                .map(flashcardMapper::toResponse)
                .toList();
    }

    @Override
    public FlashcardResponse createFlashcard(UUID lessonId, CreateFlashcardRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        courseAccessValidator.validateCreatorOrAdmin(lesson.getCourse());

        Flashcard flashcard = flashcardMapper.toEntity(request);
        flashcard.setLesson(lesson);
        if(flashcard.getOrderIndex() == null) {
            int quantityFlashcards = flashcardRepository.countByLessonId(lessonId);
            flashcard.setOrderIndex(quantityFlashcards + 1);
        }

        return flashcardMapper.toResponse(flashcardRepository.save(flashcard));
    }

    @Override
    public FlashcardResponse updateFlashcard(UUID id, UpdateFlashcardRequest request) {
        Flashcard flashcard = flashcardRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.FLASHCARD_NOT_FOUND));

        courseAccessValidator.validateCreatorOrAdmin(flashcard.getLesson().getCourse());

        flashcardMapper.updateEntity(request, flashcard);

        return flashcardMapper.toResponse(flashcardRepository.save(flashcard));
    }

    @Override
    public void reorderFlashcards(UUID lessonId, ReorderFlashcardsRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        courseAccessValidator.validateCreatorOrAdmin(lesson.getCourse());

        List<Flashcard> flashcards = flashcardRepository.findByLessonIdOrderByOrderIndexAsc(lessonId);

        if (flashcards.size() != request.flashcardIds().size()) {
            throw new AppException(ErrorCode.INVALID_FLASHCARD_REORDER);
        }

        Map<UUID, Flashcard> flashcardMap = flashcards.stream()
                .collect(Collectors.toMap(Flashcard::getId, flashcard -> flashcard));

        for(int i = 0; i < request.flashcardIds().size(); i++) {
            UUID flashcardId = request.flashcardIds().get(i);
            Flashcard flashcard = flashcardMap.get(flashcardId);
            if (flashcard == null) {
                throw new AppException(ErrorCode.FLASHCARD_NOT_FOUND);
            }
            flashcard.setOrderIndex(i + 1);
        }

        flashcardRepository.saveAll(flashcards);
    }

    @Override
    public void deleteFlashcard(UUID id) {
        Flashcard flashcard = flashcardRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.FLASHCARD_NOT_FOUND));

        courseAccessValidator.validateCreatorOrAdmin(flashcard.getLesson().getCourse());

        flashcardRepository.delete(flashcard);
    }
}
