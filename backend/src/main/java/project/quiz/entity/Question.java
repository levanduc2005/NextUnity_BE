package project.quiz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.quiz.entity.enums.QuestionType;
import project.shared.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "questions",
        indexes = {
                @Index(name = "idx_questions_quiz_id", columnList = "quiz_id")
        }
)
@Getter
@Setter
public class Question extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType type = QuestionType.SINGLE_CHOICE;

    @Column(nullable = false)
    private Integer points = 10;

    @Column(nullable = false)
    private Integer orderIndex = 1;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<QuestionOption> options = new ArrayList<>();

    public void addOption(QuestionOption option) {
        options.add(option);
        option.setQuestion(this);
    }

    public void removeOption(QuestionOption option) {
        options.remove(option);
        option.setQuestion(null);
    }
}
