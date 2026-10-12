package project.auth;

import org.junit.jupiter.api.Test;
import project.auth.util.PasswordGenerator;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    @Test
    void generateRandomPassword_defaultLength_shouldBe10Characters() {
        String password = PasswordGenerator.generateRandomPassword();
        assertNotNull(password);
        assertEquals(10, password.length());
    }

    @Test
    void generateRandomPassword_customLength_shouldMatchRequestedLength() {
        String password = PasswordGenerator.generateRandomPassword(12);
        assertNotNull(password);
        assertEquals(12, password.length());
    }

    @Test
    void generateRandomPassword_shouldContainUppercaseLowercaseAndDigits() {
        String password = PasswordGenerator.generateRandomPassword(10);
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);

        assertTrue(hasUpper, "Password should contain uppercase character");
        assertTrue(hasLower, "Password should contain lowercase character");
        assertTrue(hasDigit, "Password should contain digit");
    }
}
