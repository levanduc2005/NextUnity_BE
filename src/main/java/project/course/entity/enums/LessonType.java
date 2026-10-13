package project.course.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LessonType {
    VIDEO("Video"),
    TEXT("Bài đọc"),
    INTERACTIVE("Quiz");

    private final String displayName;
}

