package project.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.course.entity.enums.EnrollmentStatus;
import project.shared.common.BaseEntity;
import project.user.entity.User;

import java.time.Instant;

@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = @UniqueConstraint(name = "uq_enrollments_user_course", columnNames = {"user_id", "course_id"}),
        indexes = {
                @Index(name = "idx_enrollments_course_id", columnList = "course_id")
        }
)
@Getter
@Setter
public class Enrollment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private Integer progressPercent = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status = EnrollmentStatus.ENROLLED;

    private Instant completedAt;
}
