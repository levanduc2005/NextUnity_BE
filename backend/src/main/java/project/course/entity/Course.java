package project.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import project.course.entity.enums.CourseDifficulty;
import project.shared.common.SoftDeleteEntity;
import project.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@SQLDelete(sql = "UPDATE courses SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Course extends SoftDeleteEntity {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseDifficulty difficulty = CourseDifficulty.BEGINNER;

    private String thumbnailUrl;

    @Column(nullable = false)
    private Integer totalLessons = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private List<Module> modules = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();
}
