package project.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import project.auth.entity.Role;
import project.shared.common.SoftDeleteEntity;

@Entity
@Table(name = "users")
@Getter
@Setter
@SQLDelete(sql = """
    UPDATE users
    SET deleted_at = CURRENT_TIMESTAMP
    WHERE id = ?
""")
public class User extends SoftDeleteEntity {
    private String name;
    private String username;
    private String email;
    private String passwordHash;

    private String university;
    private String studentId;
    private String avatarUrl;

    @Column(nullable = false)
    private Integer points = 0;

    @Column(nullable = false)
    private Integer streak = 0;

    private String gender;
    private java.time.LocalDate dateOfBirth;
    private Integer cohort;
    private String major;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}
