package project.shared.redis;

import java.time.Duration;

public interface RedisService {
    void set(String key, Object value, Duration ttl);

    <T> T get(String key, Class<T> type);

    void delete(String key);

    boolean exists(String key);
}
