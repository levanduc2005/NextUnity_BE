package project.course.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseCategory {
    FPT_COURSES("FPT Courses"),
    SOFTWARE_ENGINEERING("Kỹ thuật phần mềm"),
    ARTIFICIAL_INTELLIGENCE("Trí tuệ nhân tạo"),
    INFORMATION_ASSURANCE("An toàn thông tin"),
    DATA_SCIENCE("Khoa học dữ liệu"),
    BUSINESS_ADMINISTRATION("Quản trị kinh doanh"),
    DIGITAL_ART_DESIGN("Thiết kế mỹ thuật số"),
    SOFT_SKILLS("Kỹ năng mềm"),
    OTHER("Khác");

    private final String displayName;
}
