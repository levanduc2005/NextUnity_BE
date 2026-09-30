package project.course.service;

import project.course.dto.mindmap.request.SaveMindmapRequest;
import project.course.dto.mindmap.response.MindmapResponse;

import java.util.UUID;

public interface MindmapService {

    MindmapResponse getMindmapByLessonId(UUID lessonId);

    MindmapResponse saveMindmap(UUID lessonId, SaveMindmapRequest request);

    void deleteMindmap(UUID lessonId);
}
