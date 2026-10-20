package project.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.auth.dto.request.GoogleLoginRequest;
import project.auth.dto.response.LoginResponse;
import project.auth.entity.RefreshToken;
import project.auth.entity.Role;
import project.auth.repository.RoleRepository;
import project.auth.security.GoogleTokenVerifier;
import project.auth.security.JwtService;
import project.auth.service.RefreshTokenService;
import project.auth.service.impl.AuthServiceImpl;
import project.shared.mail.MailService;
import project.shared.util.AvatarUtils;
import project.user.dto.response.UserResponse;
import project.user.entity.User;
import project.user.mapper.UserMapper;
import project.user.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceGoogleTest {

    @Mock
    private UserService userService;

    @Mock
    private MailService mailService;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private GoogleTokenVerifier googleTokenVerifier;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    private GoogleIdToken.Payload samplePayload;

    @BeforeEach
    void setUp() {
        samplePayload = new GoogleIdToken.Payload();
        samplePayload.setEmail("student@gmail.com");
        samplePayload.set("name", "Nguyen Van A");
    }

    @Test
    void loginWithGoogle_whenExistingUser_shouldLoginWithoutSendingEmail() {
        // Arrange
        GoogleLoginRequest request = new GoogleLoginRequest("valid_token");
        when(googleTokenVerifier.verify("valid_token")).thenReturn(samplePayload);
        when(userService.existsByEmail("student@gmail.com")).thenReturn(true);

        User existingUser = new User();
        existingUser.setEmail("student@gmail.com");
        existingUser.setFullName("Nguyen Van A");
        when(userService.getUserByEmail("student@gmail.com")).thenReturn(existingUser);

        when(jwtService.generateAccessToken(existingUser)).thenReturn("access_token_123");
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh_token_123");
        when(refreshTokenService.create(existingUser)).thenReturn(refreshToken);

        UserResponse userResponse = mock(UserResponse.class);
        when(userMapper.toResponse(existingUser)).thenReturn(userResponse);

        // Act
        LoginResponse response = authService.loginWithGoogle(request);

        // Assert
        assertNotNull(response);
        assertEquals("access_token_123", response.accessToken());
        assertEquals("refresh_token_123", response.refreshToken());
        verify(mailService, never()).sendWelcomeGoogleAccount(anyString(), anyString(), anyString());
        verify(userService, never()).saveUser(any());
    }

    @Test
    void loginWithGoogle_whenNewUser_shouldCreateUserAndSendWelcomeEmail() {
        // Arrange
        GoogleLoginRequest request = new GoogleLoginRequest("valid_new_token");
        when(googleTokenVerifier.verify("valid_new_token")).thenReturn(samplePayload);
        when(userService.existsByEmail("student@gmail.com")).thenReturn(false);

        Role studentRole = new Role();
        studentRole.setName("STUDENT");
        when(roleRepository.findByName("STUDENT")).thenReturn(Optional.of(studentRole));
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password_123");

        when(jwtService.generateAccessToken(any(User.class))).thenReturn("access_token_new");
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh_token_new");
        when(refreshTokenService.create(any(User.class))).thenReturn(refreshToken);

        UserResponse userResponse = mock(UserResponse.class);
        when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);

        // Act
        LoginResponse response = authService.loginWithGoogle(request);

        // Assert
        assertNotNull(response);
        assertEquals("access_token_new", response.accessToken());
        assertEquals("refresh_token_new", response.refreshToken());
        String expectedDefaultAvatar = AvatarUtils.generateDefaultAvatar("Nguyen Van A");
        verify(userService).saveUser(argThat(user ->
                "student@gmail.com".equals(user.getEmail()) &&
                "Nguyen Van A".equals(user.getFullName()) &&
                expectedDefaultAvatar.equals(user.getAvatarUrl()) &&
                "hashed_password_123".equals(user.getPasswordHash())
        ));
        verify(mailService).sendWelcomeGoogleAccount(eq("student@gmail.com"), eq("Nguyen Van A"), anyString());
    }

    @Test
    void loginWithGoogle_whenNewUserHasGooglePicture_shouldUsePictureAndNotGenerateInitials() {
        // Arrange
        String googlePictureUrl = "https://lh3.googleusercontent.com/a/custom-avatar-url";
        samplePayload.set("picture", googlePictureUrl);

        GoogleLoginRequest request = new GoogleLoginRequest("valid_picture_token");
        when(googleTokenVerifier.verify("valid_picture_token")).thenReturn(samplePayload);
        when(userService.existsByEmail("student@gmail.com")).thenReturn(false);

        Role studentRole = new Role();
        studentRole.setName("STUDENT");
        when(roleRepository.findByName("STUDENT")).thenReturn(Optional.of(studentRole));
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password_123");

        when(jwtService.generateAccessToken(any(User.class))).thenReturn("access_token_picture");
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh_token_picture");
        when(refreshTokenService.create(any(User.class))).thenReturn(refreshToken);

        UserResponse userResponse = mock(UserResponse.class);
        when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);

        // Act
        LoginResponse response = authService.loginWithGoogle(request);

        // Assert
        assertNotNull(response);
        verify(userService).saveUser(argThat(user ->
                "student@gmail.com".equals(user.getEmail()) &&
                "Nguyen Van A".equals(user.getFullName()) &&
                googlePictureUrl.equals(user.getAvatarUrl()) && // Avatar lấy từ Google, không bị sinh avatar chữ cái đầu đè lên
                "hashed_password_123".equals(user.getPasswordHash())
        ));
    }
}
