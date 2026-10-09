package project.shared.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shared.ratelimit.RateLimit;
import project.shared.ratelimit.RateLimitType;
import project.shared.response.ApiResponse;

@RestController
@RequestMapping("/api/public")
@Tag(name = "Health Check", description = "Endpoint kiểm tra trạng thái hoạt động của server & giữ server không ngủ")
public class HealthController {

    @RateLimit(limit = 60, durationInSeconds = 60, type = RateLimitType.IP)
    @GetMapping("/health")
    @Operation(summary = "Kiểm tra sức khỏe server (Liveness probe / Keep-alive)")
    public ResponseEntity<ApiResponse<String>> checkHealth() {
        return ResponseEntity.ok(ApiResponse.success("OK", "NextUnity Backend is healthy"));
    }
}
