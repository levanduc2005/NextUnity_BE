package project.auth.util;

import java.security.SecureRandom;

public final class PasswordGenerator {

    private static final String UPPER = "ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijkmnopqrstuvwxyz";
    private static final String DIGITS = "23456789";
    private static final String ALL_CHARS = UPPER + LOWER + DIGITS;
    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordGenerator() {}

    /**
     * Sinh mật khẩu ngẫu nhiên 10 ký tự kết hợp chữ hoa, chữ thường và chữ số,
     * loại trừ các ký tự dễ nhầm lẫn (như 0, O, 1, l, I).
     */
    public static String generateRandomPassword() {
        return generateRandomPassword(10);
    }

    public static String generateRandomPassword(int length) {
        if (length < 6) {
            length = 6;
        }

        StringBuilder sb = new StringBuilder(length);
        // Đảm bảo có ít nhất 1 chữ hoa, 1 chữ thường, 1 số
        sb.append(UPPER.charAt(RANDOM.nextInt(UPPER.length())));
        sb.append(LOWER.charAt(RANDOM.nextInt(LOWER.length())));
        sb.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));

        for (int i = 3; i < length; i++) {
            sb.append(ALL_CHARS.charAt(RANDOM.nextInt(ALL_CHARS.length())));
        }

        // Xáo trộn vị trí các ký tự
        char[] chars = sb.toString().toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        return new String(chars);
    }
}
