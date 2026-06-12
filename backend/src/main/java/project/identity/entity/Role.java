package project.identity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.shared.common.SoftDeleteEntity;
import project.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role extends SoftDeleteEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<User> users = new ArrayList<>();

}
