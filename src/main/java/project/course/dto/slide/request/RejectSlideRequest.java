package project.course.dto.slide.request;

import jakarta.validation.constraints.NotBlank;

public record RejectSlideRequest(
        @NotBlank(message = "Lý do từ chối không được để trống")
        String rejectionReason
) {}
