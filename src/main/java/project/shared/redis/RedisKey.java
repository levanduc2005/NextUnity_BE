package project.shared.redis;

public final class RedisKey {
    private RedisKey() {}

    public static final String REGISTER = "auth:register:";
    public static final String FORGOT_PASSWORD = "auth:forgot-password:";
}
