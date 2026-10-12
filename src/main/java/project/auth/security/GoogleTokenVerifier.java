package project.auth.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;

import java.util.Collections;

@Slf4j
@Component
public class GoogleTokenVerifier {

    @Value("${google.client-id:}")
    private String configuredClientId;

    public GoogleIdToken.Payload verify(String idTokenString) {
        if (idTokenString == null || idTokenString.isBlank()) {
            throw new AppException(ErrorCode.INVALID_GOOGLE_TOKEN);
        }

        try {
            GoogleIdTokenVerifier.Builder builder = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance()
            );

            if (configuredClientId != null && !configuredClientId.isBlank()) {
                builder.setAudience(Collections.singletonList(configuredClientId));
            }

            GoogleIdTokenVerifier verifier = builder.build();
            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                log.warn("Google ID Token verification failed (verifier returned null)");
                throw new AppException(ErrorCode.INVALID_GOOGLE_TOKEN);
            }

            return idToken.getPayload();
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception during Google ID Token verification: {}", e.getMessage(), e);
            throw new AppException(ErrorCode.INVALID_GOOGLE_TOKEN);
        }
    }
}
