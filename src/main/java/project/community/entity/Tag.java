package project.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import project.shared.common.BaseEntity;

@Entity
@Table(name = "tags")
@Getter
@Setter
public class Tag extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
}
