package project.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.shared.common.SoftDeleteEntity;
import project.user.entity.User;

import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment extends SoftDeleteEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reaction> reactions;
}
