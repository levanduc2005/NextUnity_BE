package project.shared.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvatarUtilsTest {

    @Test
    void generateDefaultAvatar_withNormalName_shouldEncodeProperly() {
        String avatar = AvatarUtils.generateDefaultAvatar("Nguyen Van A");
        assertNotNull(avatar);
        assertTrue(avatar.startsWith("https://ui-avatars.com/api/?name=Nguyen+Van+A"));
        assertTrue(avatar.contains("background=random"));
        assertTrue(avatar.contains("size=256"));
    }

    @Test
    void generateDefaultAvatar_withVietnameseDiacritics_shouldUrlEncode() {
        String avatar = AvatarUtils.generateDefaultAvatar("Nguyễn Văn Ánh");
        assertNotNull(avatar);
        assertTrue(avatar.startsWith("https://ui-avatars.com/api/?name="));
        assertTrue(avatar.contains("background=random"));
        assertFalse(avatar.contains(" "));
    }

    @Test
    void generateDefaultAvatar_whenNullOrBlank_shouldFallbackToUser() {
        String avatarNull = AvatarUtils.generateDefaultAvatar(null);
        assertTrue(avatarNull.startsWith("https://ui-avatars.com/api/?name=User"));

        String avatarBlank = AvatarUtils.generateDefaultAvatar("   ");
        assertTrue(avatarBlank.startsWith("https://ui-avatars.com/api/?name=User"));
    }
}
