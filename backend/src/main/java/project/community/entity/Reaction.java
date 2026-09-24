package project.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.community.entity.enums.ReactionType;
import project.shared.common.BaseEntity;
import project.user.entity.User;

@Entity
@Table(
        name = "reactions",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_reactions_user_post", columnNames = {"user_id", "post_id"}),
                @UniqueConstraint(name = "uq_reactions_user_comment", columnNames = {"user_id", "comment_id"})
        },
        indexes = {
                @Index(name = "idx_reactions_post_id", columnList = "post_id"),
                @Index(name = "idx_reactions_comment_id", columnList = "comment_id")
        }
)
@Getter
@Setter
public class Reaction extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType reactionType;

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
