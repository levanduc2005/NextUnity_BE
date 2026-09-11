package project.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.shared.common.BaseEntity;

@Entity
@Table(name = "flashcards")
@Getter
@Setter
public class Flashcard extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String front;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String back;

    @Column(nullable = false)
    private Integer orderIndex = 0;
}
