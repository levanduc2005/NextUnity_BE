package project.shared.ratelimit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;

@Component
@RequiredArgsConstructor
public class RateLimitInterceptor  implements HandlerInterceptor {

    private final RedisRateLimiter rateLimiter;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        // Bỏ qua nếu ko phải là HandlerMethod (ví dụ: static resources)
        if(!(handler instanceof HandlerMethod handlerMethod)){
            return true;
        }
        // Bỏ qua nếu ko có annotation @RateLimit
        RateLimit rateLimit = handlerMethod.getMethodAnnotation(RateLimit.class);
        if(rateLimit == null) {
            return true;
        }

        String identifier = resolveIdentifier(request, rateLimit.type());
        String key = "rate-limit:" + request.getRequestURI() + ":" + identifier;

        boolean allowed = rateLimiter.isAllowed(key, rateLimit.limit(), rateLimit.durationInSeconds());
        if(!allowed){
            throw new AppException(ErrorCode.TOO_MANY_REQUESTS);
        }

        return true;
    }

    private String resolveIdentifier(HttpServletRequest request, RateLimitType type) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal());
        if (type == RateLimitType.USER && isAuthenticated) {
            return "user:" + auth.getName();
        }
        // Mặc định lấy Client IP thật (xử lý qua proxy / Load Balancer)
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            ip = ip.split(",")[0].trim();
        }
        return "ip:" + ip;
    }
}
