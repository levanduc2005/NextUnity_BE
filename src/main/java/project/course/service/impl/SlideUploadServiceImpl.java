package project.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.course.dto.slide.request.RejectSlideRequest;
import project.course.dto.slide.request.UploadSlideRequest;
import project.course.dto.slide.response.SlideResponse;
import project.course.entity.Course;
import project.course.entity.Lesson;
import project.course.entity.SlideUpload;
import project.course.entity.enums.SlideStatus;
import project.course.mapper.SlideUploadMapper;
import project.course.repository.LessonRepository;
import project.course.repository.SlideUploadRepository;
import project.course.service.SlideUploadService;
import project.course.validator.CourseAccessValidator;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlideUploadServiceImpl implements SlideUploadService {

    private final UserService userService;

    private final SlideUploadRepository slideUploadRepository;
    private final LessonRepository lessonRepository;

    private final SlideUploadMapper slideUploadMapper;

    private final CourseAccessValidator courseAccessValidator;

    @Override
    public List<SlideResponse> getSlidesByLessonId(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        Course course = lesson.getCourse();
        courseAccessValidator.validateAccess(course);

        UUID currentUserId = SecurityUtils.getCurrentUserId();
        boolean isAdmin = SecurityUtils.isCurrentUserAdmin();

        List<SlideUpload> slides = slideUploadRepository.findByLessonId(lessonId);

        return slides.stream()
                .filter(slide -> {
                    // 1. Tài liệu do chính mình upload
                    if (slide.getUploader().getId().equals(currentUserId)) {
                        return true;
                    }
                    // 2. Tài liệu đã được duyệt công khai
                    if (slide.getStatus() == SlideStatus.PUBLIC) {
                        return true;
                    }
                    // 3. Admin: xem được các tài liệu đóng góp PENDING / REJECTED để duyệt
                    if (isAdmin && (slide.getStatus() == SlideStatus.PENDING
                            || slide.getStatus() == SlideStatus.REJECTED)) {
                        return true;
                    }
                    return false;
                })
                .map(slideUploadMapper::toResponse)
                .toList();
    }

    @Override
    public SlideResponse uploadSlide(UUID lessonId, UploadSlideRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        UUID currentUserId = SecurityUtils.getCurrentUserId();
        Course course = lesson.getCourse();
        courseAccessValidator.validateAccess(course);

        SlideUpload slideUpload = slideUploadMapper.toEntity(request);
        slideUpload.setLesson(lesson);
        slideUpload.setUploader(userService.getUserById(currentUserId));

        if (SecurityUtils.isCurrentUserAdmin()) {
            slideUpload.setStatus(SlideStatus.PUBLIC);
        } else {
            slideUpload.setStatus(SlideStatus.PRIVATE);
        }

        return slideUploadMapper.toResponse(slideUploadRepository.save(slideUpload));
    }

    @Override
    public SlideResponse contributeSlide(UUID id) {
        SlideUpload slideUpload = slideUploadRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.SLIDE_NOT_FOUND));

        UUID currentUserId = SecurityUtils.getCurrentUserId();
        if (!slideUpload.getUploader().getId().equals(currentUserId)) {
            throw new AppException(ErrorCode.NOT_SLIDE_UPLOADER);
        }

        if (slideUpload.getStatus() != SlideStatus.PRIVATE && slideUpload.getStatus() != SlideStatus.REJECTED) {
            throw new AppException(ErrorCode.INVALID_SLIDE_ACTION);
        }

        slideUpload.setStatus(SlideStatus.PENDING);
        slideUpload.setRejectionReason(null);

        return slideUploadMapper.toResponse(slideUploadRepository.save(slideUpload));
    }

    @Override
    public SlideResponse approveSlide(UUID id) {
        SlideUpload slideUpload = slideUploadRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.SLIDE_NOT_FOUND));

        if (slideUpload.getStatus() != SlideStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_SLIDE_ACTION);
        }

        slideUpload.setStatus(SlideStatus.PUBLIC);
        slideUpload.setRejectionReason(null);

        return slideUploadMapper.toResponse(slideUploadRepository.save(slideUpload));
    }

    @Override
    public SlideResponse rejectSlide(UUID id, RejectSlideRequest request) {
        SlideUpload slideUpload = slideUploadRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.SLIDE_NOT_FOUND));

        if (slideUpload.getStatus() != SlideStatus.PENDING) {
            throw new AppException(ErrorCode.INVALID_SLIDE_ACTION);
        }

        if (request.rejectionReason() == null || request.rejectionReason().isBlank()) {
            throw new AppException(ErrorCode.INVALID_SLIDE_ACTION);
        }

        slideUpload.setStatus(SlideStatus.REJECTED);
        slideUpload.setRejectionReason(request.rejectionReason());

        return slideUploadMapper.toResponse(slideUploadRepository.save(slideUpload));
    }

    @Override
    public void deleteSlide(UUID id) {
        SlideUpload slideUpload = slideUploadRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.SLIDE_NOT_FOUND));

        UUID currentUserId = SecurityUtils.getCurrentUserId();

        if (!slideUpload.getUploader().getId().equals(currentUserId) && !SecurityUtils.isCurrentUserAdmin()) {
            throw new AppException(ErrorCode.NOT_SLIDE_UPLOADER);
        }

        slideUploadRepository.delete(slideUpload);
    }
}
