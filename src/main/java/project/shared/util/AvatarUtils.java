package project.shared.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class AvatarUtils {

    private static final String UI_AVATARS_BASE_URL = "https://ui-avatars.com/api/?name=";
    private static final String UI_AVATARS_PARAMS = "&background=random&color=fff&size=256&bold=true";

    private AvatarUtils() {}

    /**
     * Sinh URL avatar mặc định dựa theo chữ cái đầu của tên (UI Avatars).
     *
     * @param fullName tên đầy đủ của người dùng
     * @return URL ảnh avatar mặc định
     */
    public static String generateDefaultAvatar(String fullName) {
        String name = (fullName != null && !fullName.isBlank()) ? fullName.trim() : "User";
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        return UI_AVATARS_BASE_URL + encodedName + UI_AVATARS_PARAMS;
    }
}
