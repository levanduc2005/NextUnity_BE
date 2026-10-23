package project.payment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.payment.dto.response.AdminPaymentResponse;
import project.payment.dto.response.MonthlyRevenueTrendResponse;
import project.payment.dto.response.RevenueOverviewResponse;
import project.payment.entity.Payment;
import project.payment.entity.enums.PaymentStatus;
import project.payment.entity.enums.SubscriptionPlan;
import project.payment.mapper.PaymentMapper;
import project.payment.repository.PaymentRepository;
import project.payment.service.AdminPaymentService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminPaymentServiceImpl implements AdminPaymentService {

    private static final ZoneId VN_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");
    private static final DateTimeFormatter MONTH_LABEL_FORMATTER = DateTimeFormatter.ofPattern("'Tháng 'MM/yyyy");

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public Page<AdminPaymentResponse> getAllPayments(
            PaymentStatus status,
            SubscriptionPlan plan,
            String search,
            Instant fromDate,
            Instant toDate,
            Pageable pageable
    ) {
        String trimmedSearch = (search != null && !search.trim().isEmpty()) ? search.trim() : null;
        Page<Payment> page = paymentRepository.searchPayments(status, plan, trimmedSearch, fromDate, toDate, pageable);
        return page.map(paymentMapper::toAdminResponse);
    }

    @Override
    public AdminPaymentResponse getPaymentDetail(UUID id) {
        Payment payment = paymentRepository.findWithUserById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));
        return paymentMapper.toAdminResponse(payment);
    }

    @Override
    public RevenueOverviewResponse getRevenueOverview() {
        ZonedDateTime nowVn = ZonedDateTime.now(VN_ZONE);

        // Mốc thời gian bắt đầu ngày hôm nay (00:00:00 VN)
        Instant startOfToday = nowVn.truncatedTo(ChronoUnit.DAYS).toInstant();
        Instant endOfToday = nowVn.truncatedTo(ChronoUnit.DAYS).plusDays(1).toInstant();

        // Mốc thời gian bắt đầu tháng hiện tại (ngày 1 đầu tháng, 00:00:00 VN)
        Instant startOfMonth = nowVn.withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS).toInstant();
        Instant endOfMonth = nowVn.withDayOfMonth(1).plusMonths(1).truncatedTo(ChronoUnit.DAYS).toInstant();

        Long totalRevenue = paymentRepository.getTotalRevenue();
        Long todayRevenue = paymentRepository.getRevenueBetween(startOfToday, endOfToday);
        Long thisMonthRevenue = paymentRepository.getRevenueBetween(startOfMonth, endOfMonth);

        long paidCount = paymentRepository.countByStatus(PaymentStatus.PAID);
        long pendingCount = paymentRepository.countByStatus(PaymentStatus.PENDING);
        long cancelledCount = paymentRepository.countByStatus(PaymentStatus.CANCELLED);
        long expiredCount = paymentRepository.countByStatus(PaymentStatus.EXPIRED);
        long totalTransactions = paymentRepository.count();

        double successRate = totalTransactions > 0
                ? Math.round(((double) paidCount / totalTransactions * 100.0) * 10.0) / 10.0
                : 0.0;

        // Khởi tạo map doanh thu và số lượng theo gói
        Map<String, Long> revenueByPlan = new LinkedHashMap<>();
        Map<String, Long> countByPlan = new LinkedHashMap<>();
        for (SubscriptionPlan plan : SubscriptionPlan.values()) {
            revenueByPlan.put(plan.name(), 0L);
            countByPlan.put(plan.name(), 0L);
        }

        List<Object[]> planStats = paymentRepository.getRevenueByPlan();
        for (Object[] row : planStats) {
            if (row != null && row.length >= 3 && row[0] instanceof SubscriptionPlan plan) {
                Long rev = row[1] instanceof Number n ? n.longValue() : 0L;
                Long cnt = row[2] instanceof Number n ? n.longValue() : 0L;
                revenueByPlan.put(plan.name(), rev);
                countByPlan.put(plan.name(), cnt);
            }
        }

        return new RevenueOverviewResponse(
                totalRevenue != null ? totalRevenue : 0L,
                todayRevenue != null ? todayRevenue : 0L,
                thisMonthRevenue != null ? thisMonthRevenue : 0L,
                totalTransactions,
                paidCount,
                pendingCount,
                cancelledCount + expiredCount,
                successRate,
                revenueByPlan,
                countByPlan
        );
    }

    @Override
    public List<MonthlyRevenueTrendResponse> getRevenueTrend(int months) {
        int safeMonths = Math.max(1, Math.min(months, 24));
        ZonedDateTime nowVn = ZonedDateTime.now(VN_ZONE);
        YearMonth currentMonth = YearMonth.from(nowVn);
        YearMonth startMonth = currentMonth.minusMonths(safeMonths - 1);

        Instant since = startMonth.atDay(1).atStartOfDay(VN_ZONE).toInstant();
        List<Payment> paidPayments = paymentRepository.findPaidPaymentsSince(since);

        Map<YearMonth, Long> revenueMap = new HashMap<>();
        Map<YearMonth, Long> countMap = new HashMap<>();

        for (Payment payment : paidPayments) {
            if (payment.getCreatedAt() != null) {
                YearMonth ym = YearMonth.from(payment.getCreatedAt().atZone(VN_ZONE));
                revenueMap.merge(ym, payment.getAmount() != null ? payment.getAmount() : 0L, Long::sum);
                countMap.merge(ym, 1L, Long::sum);
            }
        }

        List<MonthlyRevenueTrendResponse> trend = new ArrayList<>();
        for (int i = 0; i < safeMonths; i++) {
            YearMonth ym = startMonth.plusMonths(i);
            long rev = revenueMap.getOrDefault(ym, 0L);
            long cnt = countMap.getOrDefault(ym, 0L);
            trend.add(new MonthlyRevenueTrendResponse(
                    ym.toString(),
                    ym.format(MONTH_LABEL_FORMATTER),
                    rev,
                    cnt
            ));
        }

        return trend;
    }
}
