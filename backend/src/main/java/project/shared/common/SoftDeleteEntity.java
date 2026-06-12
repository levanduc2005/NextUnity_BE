package project.shared.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
public abstract class SoftDeleteEntity extends BaseEntity {

    @Column(name = "deleted_at")
    private Instant deletedAt;

}
