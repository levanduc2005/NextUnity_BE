package project.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import project.shared.common.SoftDeleteEntity;
import project.user.entity.User;

import java.util.List;

@Entity
@Table(
        name = "communities",
        indexes = {
                @Index(name = "idx_communities_creator_id", columnList = "creator_id")
        }
)
@Getter
@Setter
@SQLDelete(sql = "UPDATE communities SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Community extends SoftDeleteEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityMember> members;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<Post> posts;
}
