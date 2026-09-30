package project.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.course.entity.enums.SlideStatus;
import project.shared.common.BaseEntity;
import project.user.entity.User;

@Entity
@Table(
        name = "slide_uploads",
        indexes = {
                @Index(name = "idx_slide_uploads_lesson_id", columnList = "lesson_id"),
                @Index(name = "idx_slide_uploads_uploader_id", columnList = "uploader_id")
        }
)
@Getter
@Setter
public class SlideUpload extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id", nullable = false)
    private User uploader;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlideStatus status = SlideStatus.PRIVATE;

    private String rejectionReason;
}
