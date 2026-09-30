package project.quiz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.shared.common.BaseEntity;

@Entity
@Table(
        name = "question_options",
        indexes = {
                @Index(name = "idx_question_options_question_id", columnList = "question_id")
        }
)
@Getter
@Setter
public class QuestionOption extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Boolean isCorrect = false;

    @Column(nullable = false)
    private Integer orderIndex = 1;
}
