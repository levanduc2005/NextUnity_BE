package project.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.course.entity.enums.LessonProgressStatus;
import project.shared.common.BaseEntity;
import project.user.entity.User;

import java.time.Instant;

@Entity
@Table(
        name = "lesson_progress",
        uniqueConstraints = @UniqueConstraint(name = "uq_lesson_progress_user_lesson", columnNames = {"user_id", "lesson_id"}),
        indexes = {
                @Index(name = "idx_lesson_progress_lesson_id", columnList = "lesson_id")
        }
)
@Getter
@Setter
public class LessonProgress extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LessonProgressStatus status = LessonProgressStatus.PENDING;

    private Instant completedAt;
}
