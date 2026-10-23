package project.payment.dto.response;

import java.util.Map;

public record RevenueOverviewResponse(
        Long totalRevenue,
        Long todayRevenue,
        Long thisMonthRevenue,
        Long totalTransactions,
        Long paidTransactions,
        Long pendingTransactions,
        Long cancelledOrExpiredTransactions,
        Double successRate,
        Map<String, Long> revenueByPlan,
        Map<String, Long> countByPlan
) {}
