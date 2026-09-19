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
import project.course.repository.EnrollmentRepository;
import project.course.repository.LessonRepository;
import project.course.repository.SlideUploadRepository;
import project.course.service.SlideUploadService;
import project.course.validator.CourseAccessValidator;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;
import project.shared.util.SecurityUtils;
import project.user.repository.UserRepository;
import project.user.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlideUploadServiceImpl implements SlideUploadService {

    private final UserService userService;

    private final SlideUploadRepository slideUploadRepository;
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    private final SlideUploadMapper slideUploadMapper;

    private final CourseAccessValidator courseAccessValidator;

    @Override
    public List<SlideResponse> getSlidesByLessonId(UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new AppException(ErrorCode.LESSON_NOT_FOUND));

        Course course = lesson.getCourse();
        courseAccessValidator.validateAccess(course);

        UUID currentUserId = SecurityUtils.getCurrentUserId();
        boolean isCreatorOrAdmin = courseAccessValidator.isCreatorOrAdmin(course);

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
                    // 3. Giảng viên/Admin: xem được các tài liệu đóng góp PENDING / REJECTED để
                    // duyệt
                    if (isCreatorOrAdmin && (slide.getStatus() == SlideStatus.PENDING
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

        if (courseAccessValidator.isCreatorOrAdmin(course)) {
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

        courseAccessValidator.validateCreatorOrAdmin(slideUpload.getLesson().getCourse());

        if(slideUpload.getStatus() != SlideStatus.PENDING) {
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

        courseAccessValidator.validateCreatorOrAdmin(slideUpload.getLesson().getCourse());

        if(slideUpload.getStatus() != SlideStatus.PENDING) {
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
        Course course = slideUpload.getLesson().getCourse();

        if (!slideUpload.getUploader().getId().equals(currentUserId)) {
            courseAccessValidator.validateCreatorOrAdmin(course);
        }

        slideUploadRepository.delete(slideUpload);
    }
}
