package project.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import project.shared.common.SoftDeleteEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "modules",
        indexes = {
                @Index(name = "idx_modules_course_id", columnList = "course_id")
        }
)
@Getter
@Setter
@SQLDelete(sql = "UPDATE modules SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Module extends SoftDeleteEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer orderIndex = 1;

    @Column(nullable = false)
    private Boolean isLocked = false;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    @SQLRestriction("deleted_at IS NULL")
    private List<Lesson> lessons = new ArrayList<>();
}
