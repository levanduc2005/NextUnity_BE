package project.course.service;

import project.course.dto.slide.request.RejectSlideRequest;
import project.course.dto.slide.request.UploadSlideRequest;
import project.course.dto.slide.response.SlideResponse;

import java.util.List;
import java.util.UUID;

public interface SlideUploadService {

    List<SlideResponse> getSlidesByLessonId(UUID lessonId);

    SlideResponse uploadSlide(UUID lessonId, UploadSlideRequest request);

    SlideResponse contributeSlide(UUID id);

    SlideResponse approveSlide(UUID id);

    SlideResponse rejectSlide(UUID id, RejectSlideRequest request);

    void deleteSlide(UUID id);
}
