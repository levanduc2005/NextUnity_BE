package project.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.payment.dto.response.AdminPaymentResponse;
import project.payment.dto.response.MonthlyRevenueTrendResponse;
import project.payment.dto.response.RevenueOverviewResponse;
import project.payment.entity.enums.PaymentStatus;
import project.payment.entity.enums.SubscriptionPlan;
import project.payment.service.AdminPaymentService;
import project.shared.response.ApiResponse;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/payments")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminPaymentController {

    private final AdminPaymentService adminPaymentService;

    @GetMapping
    public ApiResponse<Page<AdminPaymentResponse>> getAllPayments(
            @RequestParam(required = false) PaymentStatus status,
            @RequestParam(required = false) SubscriptionPlan plan,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Instant fromDate,
            @RequestParam(required = false) Instant toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<AdminPaymentResponse> response = adminPaymentService.getAllPayments(status, plan, search, fromDate, toDate, pageable);
        return ApiResponse.success(response, "Lấy danh sách giao dịch thanh toán thành công");
    }

    @GetMapping("/{id}")
    public ApiResponse<AdminPaymentResponse> getPaymentDetail(@PathVariable UUID id) {
        AdminPaymentResponse response = adminPaymentService.getPaymentDetail(id);
        return ApiResponse.success(response, "Lấy chi tiết giao dịch thanh toán thành công");
    }

    @GetMapping("/revenue/overview")
    public ApiResponse<RevenueOverviewResponse> getRevenueOverview() {
        RevenueOverviewResponse response = adminPaymentService.getRevenueOverview();
        return ApiResponse.success(response, "Lấy báo cáo tổng quan doanh thu thành công");
    }

    @GetMapping("/revenue/trend")
    public ApiResponse<List<MonthlyRevenueTrendResponse>> getRevenueTrend(
            @RequestParam(defaultValue = "6") int months
    ) {
        List<MonthlyRevenueTrendResponse> response = adminPaymentService.getRevenueTrend(months);
        return ApiResponse.success(response, "Lấy dữ liệu biểu đồ xu hướng doanh thu thành công");
    }
}
