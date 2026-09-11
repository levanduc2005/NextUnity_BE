package project.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import project.shared.common.SoftDeleteEntity;

@Entity
@Getter
@Setter
@Table(name = "roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = """
    UPDATE roles
    SET deleted_at = CURRENT_TIMESTAMP
    WHERE id = ?
""")
public class Role extends SoftDeleteEntity {

    @Column(nullable = false)
    private String name;

    private String description;
}
