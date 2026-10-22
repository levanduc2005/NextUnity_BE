package project.payment.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubscriptionPlan {
    PLUS_MONTHLY("PLUS_MONTHLY", "Gói Plus 1 Tháng", 2000L, 30, "Mở khóa toàn bộ khóa học và full tính năng AI trong 30 ngày"),
    PLUS_SEMESTER("PLUS_SEMESTER", "Gói Plus 1 Kỳ (4 Tháng)", 5000L, 120, "Tiết kiệm 25%, học trọn vẹn toàn bộ môn học trong kỳ với full AI");

    private final String code;
    private final String name;
    private final Long price;
    private final Integer durationDays;
    private final String description;
}
