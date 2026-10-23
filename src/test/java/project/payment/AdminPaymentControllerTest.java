package project.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.payment.controller.AdminPaymentController;
import project.payment.dto.response.AdminPaymentResponse;
import project.payment.dto.response.MonthlyRevenueTrendResponse;
import project.payment.dto.response.RevenueOverviewResponse;
import project.payment.entity.enums.PaymentStatus;
import project.payment.entity.enums.SubscriptionPlan;
import project.payment.service.AdminPaymentService;
import project.shared.response.ApiResponse;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminPaymentControllerTest {

    @Mock
    private AdminPaymentService adminPaymentService;

    @InjectMocks
    private AdminPaymentController adminPaymentController;

    private AdminPaymentResponse sampleResponse;

    @BeforeEach
    void setUp() {
        sampleResponse = new AdminPaymentResponse(
                UUID.randomUUID(),
                123456L,
                2000L,
                PaymentStatus.PAID,
                SubscriptionPlan.PLUS_MONTHLY,
                "Gói Plus 1 Tháng",
                UUID.randomUUID(),
                "Nguyen Van A",
                "a@gmail.com",
                "https://avatar.com/1",
                Instant.now().plusSeconds(900),
                Instant.now(),
                Instant.now()
        );
    }

    @Test
    void getAllPayments_ShouldReturnPagedPayments() {
        Page<AdminPaymentResponse> page = new PageImpl<>(List.of(sampleResponse));
        when(adminPaymentService.getAllPayments(any(), any(), any(), any(), any(), any(Pageable.class)))
                .thenReturn(page);

        ApiResponse<Page<AdminPaymentResponse>> response = adminPaymentController.getAllPayments(
                PaymentStatus.PAID, SubscriptionPlan.PLUS_MONTHLY, "Nguyen", null, null, 0, 10, "createdAt", "desc"
        );

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(1, response.getData().getContent().size());
        assertEquals(123456L, response.getData().getContent().get(0).orderCode());
        verify(adminPaymentService, times(1)).getAllPayments(any(), any(), any(), any(), any(), any(Pageable.class));
    }

    @Test
    void getPaymentDetail_ShouldReturnPaymentDetail() {
        UUID id = sampleResponse.id();
        when(adminPaymentService.getPaymentDetail(id)).thenReturn(sampleResponse);

        ApiResponse<AdminPaymentResponse> response = adminPaymentController.getPaymentDetail(id);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("Nguyen Van A", response.getData().userFullName());
        verify(adminPaymentService, times(1)).getPaymentDetail(id);
    }

    @Test
    void getRevenueOverview_ShouldReturnOverviewData() {
        RevenueOverviewResponse overview = new RevenueOverviewResponse(
                1000000L, 50000L, 200000L, 100L, 85L, 10L, 5L, 85.0,
                Map.of("PLUS_MONTHLY", 600000L, "PLUS_SEMESTER", 400000L),
                Map.of("PLUS_MONTHLY", 60L, "PLUS_SEMESTER", 25L)
        );
        when(adminPaymentService.getRevenueOverview()).thenReturn(overview);

        ApiResponse<RevenueOverviewResponse> response = adminPaymentController.getRevenueOverview();

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(1000000L, response.getData().totalRevenue());
        assertEquals(85.0, response.getData().successRate());
        verify(adminPaymentService, times(1)).getRevenueOverview();
    }

    @Test
    void getRevenueTrend_ShouldReturnMonthlyTrendList() {
        MonthlyRevenueTrendResponse trendItem = new MonthlyRevenueTrendResponse(
                "2026-05", "Tháng 05/2026", 500000L, 25L
        );
        when(adminPaymentService.getRevenueTrend(6)).thenReturn(List.of(trendItem));

        ApiResponse<List<MonthlyRevenueTrendResponse>> response = adminPaymentController.getRevenueTrend(6);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(1, response.getData().size());
        assertEquals("2026-05", response.getData().get(0).month());
        verify(adminPaymentService, times(1)).getRevenueTrend(6);
    }
}
