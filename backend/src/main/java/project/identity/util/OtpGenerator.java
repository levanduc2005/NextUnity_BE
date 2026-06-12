package project.identity.util;

import java.security.SecureRandom;

public final class OtpGenerator {

    private static final SecureRandom random = new SecureRandom();

    private OtpGenerator() {}

    public static String generate6DigitOtp() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
