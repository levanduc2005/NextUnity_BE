package project.quiz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.shared.common.BaseEntity;

@Entity
@Table(
        name = "quiz_submission_options",
        uniqueConstraints = @UniqueConstraint(name = "uq_submission_option", columnNames = {"submission_id", "selected_option_id"}),
        indexes = {
                @Index(name = "idx_submission_options_option_id", columnList = "selected_option_id")
        }
)
@Getter
@Setter
public class QuizSubmissionOption extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id", nullable = false)
    private QuizSubmission submission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_option_id", nullable = false)
    private QuestionOption selectedOption;

    @Column(nullable = false)
    private Boolean isCorrect = false;

    public Question getQuestion() {
        return selectedOption != null ? selectedOption.getQuestion() : null;
    }
}
