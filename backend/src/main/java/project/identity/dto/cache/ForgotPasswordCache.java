package project.identity.dto.cache;

import lombok.Builder;

@Builder
public record ForgotPasswordCache(
        String otp,
        boolean verified
) {
}
