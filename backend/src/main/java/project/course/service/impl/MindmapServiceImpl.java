package project.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.course.dto.mindmap.request.SaveMindmapRequest;
import project.course.dto.mindmap.response.MindmapResponse;
import project.course.entity.Lesson;
import project.course.entity.Mindmap;
import project.course.mapper.MindmapMapper;
import project.course.repository.LessonRepository;
import project.course.repository.MindmapRepository;
import project.course.service.MindmapService;
import project.course.validator.CourseAccessValidator;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MindmapServiceImpl implements MindmapService {

    private final MindmapRepository mindmapRepository;
    private final LessonRepository lessonRepository;

    private final MindmapMapper mindmapMapper;

    private final CourseAccessValidator courseAccessValidator;

    @Override
    public MindmapResponse getMindmapByLessonId(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        courseAccessValidator.validateAccess(lesson.getCourse());

        Mindmap mindmap = mindmapRepository.findByLessonId(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.MINDMAP_NOT_FOUND));

        return mindmapMapper.toResponse(mindmap);
    }

    @Override
    public MindmapResponse saveMindmap(UUID lessonId, SaveMindmapRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        courseAccessValidator.validateCreatorOrAdmin(lesson.getCourse());

         Mindmap mindmap = mindmapRepository.findByLessonId(lessonId)
                 .map(existing -> {
                     mindmapMapper.updateEntity(request, existing);
                        return existing;
                 })
                 .orElseGet(() -> {
                        Mindmap newMindmap = mindmapMapper.toEntity(request);
                        newMindmap.setLesson(lesson);
                        return newMindmap;
                 });

        return mindmapMapper.toResponse(mindmapRepository.save(mindmap));
    }

    @Override
    public void deleteMindmap(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        courseAccessValidator.validateCreatorOrAdmin(lesson.getCourse());

        Mindmap mindmap = mindmapRepository.findByLessonId(lessonId)
                .orElseThrow(() -> new AppException(ErrorCode.MINDMAP_NOT_FOUND));

        mindmapRepository.delete(mindmap);
    }
}
