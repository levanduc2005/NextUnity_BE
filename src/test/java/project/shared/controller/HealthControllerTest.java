package project.shared.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.shared.response.ApiResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HealthControllerTest {

    @Test
    void testCheckHealth() {
        HealthController controller = new HealthController();
        ResponseEntity<ApiResponse<String>> response = controller.checkHealth();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("OK", response.getBody().getData());
        assertEquals("NextUnity Backend is healthy", response.getBody().getMessage());
    }
}
