package project.community.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.community.entity.enums.CommunityRole;
import project.shared.common.BaseEntity;
import project.user.entity.User;

@Entity
@Table(name = "community_members",
        uniqueConstraints = @UniqueConstraint(columnNames = {"community_id", "user_id"}))
@Getter
@Setter
public class CommunityMember extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommunityRole role = CommunityRole.MEMBER;
}
