package project.shared.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import project.auth.security.CustomUserDetails;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;

import java.util.UUID;

public class SecurityUtils {

    private SecurityUtils() {}

    public static CustomUserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof CustomUserDetails user)) {

            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return user;
    }

    public static UUID getCurrentUserId() {
        return getCurrentUserDetails().getUserId();
    }
}
