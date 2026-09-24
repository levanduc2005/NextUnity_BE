package project.quiz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.shared.common.BaseEntity;
import project.user.entity.User;

import java.time.Instant;

@Entity
@Table(
        name = "quiz_submissions",
        indexes = {
                @Index(name = "idx_quiz_submissions_user_quiz", columnList = "user_id, quiz_id"),
                @Index(name = "idx_quiz_submissions_quiz_id", columnList = "quiz_id")
        }
)
@Getter
@Setter
public class QuizSubmission extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer score = 0;

    @Column(nullable = false)
    private Integer totalPoints = 0;

    @Column(nullable = false)
    private Boolean isPassed = false;

    private Integer timeSpentSeconds;

    @Column(nullable = false)
    private Instant submittedAt = Instant.now();

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<QuizSubmissionOption> submissionOptions = new java.util.ArrayList<>();

    public void addSubmissionOption(QuizSubmissionOption option) {
        submissionOptions.add(option);
        option.setSubmission(this);
    }
}
