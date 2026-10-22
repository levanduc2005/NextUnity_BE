package project.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import project.auth.entity.Role;
import project.shared.common.SoftDeleteEntity;
import project.shared.util.AvatarUtils;
import project.user.entity.enums.SubscriptionTier;
import project.user.entity.enums.UserGender;

import java.time.Instant;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_role_id", columnList = "role_id")
})
@Getter
@Setter
@SQLDelete(sql = """
            UPDATE users
            SET deleted_at = CURRENT_TIMESTAMP
            WHERE id = ?
        """)
public class User extends SoftDeleteEntity {
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private String university;
    private String studentId;
    private String avatarUrl;

    @Column(nullable = false)
    private Integer streak = 0;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserGender gender;

    private java.time.LocalDate dateOfBirth;
    private Integer cohort;
    private String major;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionTier subscriptionTier = SubscriptionTier.FREE;

    private Instant premiumUntil;

    public boolean isPlus() {
        return subscriptionTier == SubscriptionTier.PLUS
                && premiumUntil != null
                && premiumUntil.isAfter(Instant.now());
    }

    @PrePersist
    public void setInitialDefaultAvatar() {
        if (this.avatarUrl == null || this.avatarUrl.isBlank()) {
            this.avatarUrl = AvatarUtils.generateDefaultAvatar(this.fullName);
        }
    }
}
