package project.payment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.payment.entity.enums.PaymentStatus;
import project.payment.entity.enums.SubscriptionPlan;
import project.shared.common.BaseEntity;
import project.user.entity.User;

@Entity
@Table(
        name = "payments",
        indexes = {
                @Index(name = "idx_payments_order_code", columnList = "order_code", unique = true),
                @Index(name = "idx_payments_user_id", columnList = "user_id")
        }
)
@Getter
@Setter
public class Payment extends BaseEntity {

    @Column(name = "order_code", nullable = false, unique = true)
    private Long orderCode;

    @Column(nullable = false)
    private Long amount;

    @Column(columnDefinition = "TEXT")
    private String checkoutUrl;

    @Column(columnDefinition = "TEXT")
    private String qrCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionPlan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
