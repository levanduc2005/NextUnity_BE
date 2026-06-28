package project.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.community.entity.enums.VoteType;
import project.shared.common.BaseEntity;
import project.user.entity.User;

@Entity
@Table(name = "votes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "post_id"}),
                @UniqueConstraint(columnNames = {"user_id", "comment_id"})
        })
@Getter
@Setter
public class Vote extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type", nullable = false)
    private VoteType voteType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
