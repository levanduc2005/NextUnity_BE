package project.payment.dto.response;

public record MonthlyRevenueTrendResponse(
        String month,
        String label,
        Long revenue,
        Long transactionCount
) {}
