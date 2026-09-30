package project.payment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.payment.entity.Payment;
import project.payment.entity.enums.PaymentStatus;

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
}
