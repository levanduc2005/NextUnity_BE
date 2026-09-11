package project.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.shared.common.BaseEntity;

@Entity
@Table(name = "mindmaps")
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
