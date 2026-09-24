package project.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.shared.common.BaseEntity;

@Entity
@Table(
        name = "mindmaps",
        indexes = {
                @Index(name = "idx_mindmaps_lesson_id", columnList = "lesson_id")
        }
)
@Getter
@Setter
public class Mindmap extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String data;
}
