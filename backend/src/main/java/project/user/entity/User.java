package project.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import project.identity.entity.Role;
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
@SQLRestriction("deleted_at IS NULL")
public class User extends SoftDeleteEntity {
    private String username;
    private String email;
    private String passwordHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}
