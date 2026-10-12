package project.course.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseDifficulty {
    BEGINNER("Cơ bản"),
    INTERMEDIATE("Trung cấp"),
    ADVANCED("Nâng cao");

    private final String displayName;
}
