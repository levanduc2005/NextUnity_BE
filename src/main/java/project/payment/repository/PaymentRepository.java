package project.payment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.payment.entity.Payment;
import project.payment.entity.enums.PaymentStatus;
import project.payment.entity.enums.SubscriptionPlan;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    Optional<Payment> findByOrderCode(Long orderCode);

    Optional<Payment> findFirstByUserIdAndStatusOrderByCreatedAtDesc(UUID userId, PaymentStatus status);

    Page<Payment> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

    List<Payment> findByStatusAndCreatedAtBefore(PaymentStatus status, Instant before);

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT p FROM Payment p WHERE p.id = :id")
    Optional<Payment> findWithUserById(@Param("id") UUID id);

    @EntityGraph(attributePaths = {"user"})
    @Query("""
        SELECT p FROM Payment p
        WHERE (:status IS NULL OR p.status = :status)
          AND (:plan IS NULL OR p.plan = :plan)
          AND (CAST(:search AS string) IS NULL OR 
               LOWER(p.user.fullName) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%')) OR 
               LOWER(p.user.email) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%')) OR 
               CAST(p.orderCode AS string) LIKE CONCAT('%', CAST(:search AS string), '%'))
          AND (p.createdAt >= :fromDate OR CAST(:fromDate AS timestamp) IS NULL)
          AND (p.createdAt <= :toDate OR CAST(:toDate AS timestamp) IS NULL)
    """)
    Page<Payment> searchPayments(
            @Param("status") PaymentStatus status,
            @Param("plan") SubscriptionPlan plan,
            @Param("search") String search,
            @Param("fromDate") Instant fromDate,
            @Param("toDate") Instant toDate,
            Pageable pageable
    );

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.status = project.payment.entity.enums.PaymentStatus.PAID")
    Long getTotalRevenue();

    @Query("""
        SELECT COALESCE(SUM(p.amount), 0) FROM Payment p 
        WHERE p.status = project.payment.entity.enums.PaymentStatus.PAID 
          AND p.createdAt >= :from 
          AND p.createdAt < :to
    """)
    Long getRevenueBetween(@Param("from") Instant from, @Param("to") Instant to);

    Long countByStatus(PaymentStatus status);

    @Query("""
        SELECT p FROM Payment p 
        WHERE p.status = project.payment.entity.enums.PaymentStatus.PAID 
          AND p.createdAt >= :since 
        ORDER BY p.createdAt ASC
    """)
    List<Payment> findPaidPaymentsSince(@Param("since") Instant since);

    @Query("""
        SELECT p.plan, COALESCE(SUM(p.amount), 0), COUNT(p) 
        FROM Payment p 
        WHERE p.status = project.payment.entity.enums.PaymentStatus.PAID 
        GROUP BY p.plan
    """)
    List<Object[]> getRevenueByPlan();
}
