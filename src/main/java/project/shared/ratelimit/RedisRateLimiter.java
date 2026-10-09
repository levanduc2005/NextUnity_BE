package project.shared.ratelimit;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class RedisRateLimiter {

    private final StringRedisTemplate stringRedisTemplate;

    public boolean isAllowed(String key, int limit, int durationInSeconds) {
        long now = Instant.now().toEpochMilli();
        long windowStart = now - (durationInSeconds * 1000L);

        // dọn dẹp các key cũ
        stringRedisTemplate.opsForZSet().removeRangeByScore(key, 0, windowStart);

        // kiểm tra số lượng
        long currentRequests = stringRedisTemplate.opsForZSet().zCard(key);
        if(currentRequests >= limit) {
            return false;
        }

        // Thêm request mới vào
        stringRedisTemplate.opsForZSet().add(key, String.valueOf(now), now);

        // đặt thời gian sống cho key
        stringRedisTemplate.expire(key, Duration.ofSeconds(durationInSeconds * 2L));

        return true;
    }
}
