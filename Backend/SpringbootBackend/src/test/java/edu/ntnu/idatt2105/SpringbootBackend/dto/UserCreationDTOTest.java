package edu.ntnu.idatt2105.SpringbootBackend.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserCreationDTOTest {

    @Test
    void testNoArgsConstructor() {
        UserCreationDTO user = new UserCreationDTO();
        assertNotNull(user);
    }

    @Test
    void testAllArgsConstructor() {
        UserCreationDTO user = new UserCreationDTO("johnDoe", "Password123", "john.doe@example.com");
        assertEquals("johnDoe", user.getUsername());
        assertEquals("Password123", user.getPassword());
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    void testFieldAssignments() {
        UserCreationDTO user = new UserCreationDTO();
        
        user.setUsername("janeDoe");
        user.setPassword("SecurePassword123");
        user.setEmail("jane.doe@example.com");
        
        assertEquals("janeDoe", user.getUsername());
        assertEquals("SecurePassword123", user.getPassword());
        assertEquals("jane.doe@example.com", user.getEmail());
    }
}
