package project.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import project.course.entity.enums.LessonType;
import project.quiz.entity.Quiz;
import project.shared.common.SoftDeleteEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "lessons",
        indexes = {
                @Index(name = "idx_lessons_module_id", columnList = "module_id")
        }
)
@Getter
@Setter
@SQLDelete(sql = "UPDATE lessons SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Lesson extends SoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Integer orderIndex = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LessonType type = LessonType.TEXT;

    @Column(name = "estimated_minutes", nullable = false)
    private Integer estimatedMinutes = 15;

    private String videoUrl;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SlideUpload> slides = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<Flashcard> flashcards = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mindmap> mindmaps = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonProgress> progresses = new ArrayList<>();

    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Quiz quiz;

    public Course getCourse() {
        return module != null ? module.getCourse() : null;
    }
}
