package project.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.identity.entity.Role;
import project.shared.common.SoftDeleteEntity;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends SoftDeleteEntity {
    private String username;
    private String email;
    private String passwordHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}
