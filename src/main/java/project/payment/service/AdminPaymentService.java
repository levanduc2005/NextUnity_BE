package project.payment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.payment.dto.response.AdminPaymentResponse;
import project.payment.dto.response.MonthlyRevenueTrendResponse;
import project.payment.dto.response.RevenueOverviewResponse;
import project.payment.entity.enums.PaymentStatus;
import project.payment.entity.enums.SubscriptionPlan;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AdminPaymentService {

    Page<AdminPaymentResponse> getAllPayments(
            PaymentStatus status,
            SubscriptionPlan plan,
            String search,
            Instant fromDate,
            Instant toDate,
            Pageable pageable
    );

    AdminPaymentResponse getPaymentDetail(UUID id);

    RevenueOverviewResponse getRevenueOverview();

    List<MonthlyRevenueTrendResponse> getRevenueTrend(int months);
}
