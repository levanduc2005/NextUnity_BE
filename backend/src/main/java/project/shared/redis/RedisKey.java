package project.shared.redis;

public final class RedisKey {
    private RedisKey() {}
    // register flow
    public static final String REGISTER = "auth:register:";
    public static final String REGISTER_COOLDOWN = "auth:register:cooldown:";

    // login/session
    public static final String REFRESH_TOKEN = "auth:token:refresh:";
    public static final String SESSION = "auth:session:";

    // security
    public static final String OTP = "auth:otp:";
    public static final String BLACKLIST = "auth:blacklist:";
    public static final String RATE_LIMIT = "auth:rate:";
}
