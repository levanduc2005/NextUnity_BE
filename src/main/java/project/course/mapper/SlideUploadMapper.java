package project.course.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.course.dto.slide.request.UploadSlideRequest;
import project.course.dto.slide.response.SlideResponse;
import project.course.entity.SlideUpload;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SlideUploadMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "uploader", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "rejectionReason", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SlideUpload toEntity(UploadSlideRequest request);

    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "uploaderId", source = "uploader.id")
    @Mapping(target = "uploaderName", source = "uploader.username")
    SlideResponse toResponse(SlideUpload slideUpload);

    List<SlideResponse> toResponseList(List<SlideUpload> slideUploads);
}
