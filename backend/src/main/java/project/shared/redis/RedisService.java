package project.shared.redis;

public interface RedisService {
    void set(String key, Object value, java.time.Duration ttl);

    <T> T get(String key, Class<T> type);

    void delete(String key);

    boolean exists(String key);
}
