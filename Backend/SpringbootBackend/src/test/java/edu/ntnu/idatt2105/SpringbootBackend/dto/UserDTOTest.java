package edu.ntnu.idatt2105.SpringbootBackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void testNoArgsConstructor() {
        UserDTO user = new UserDTO();
        assertNotNull(user);
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }

    @Test
    void testAllArgsConstructor() {
        UserDTO user = new UserDTO("johnDoe", "Password123");
        assertEquals("johnDoe", user.getUsername());
        assertEquals("Password123", user.getPassword());
    }

    @Test
    void testFieldAssignments() {
        UserDTO user = new UserDTO();
        
        user.setUsername("janeDoe");
        user.setPassword("SecurePassword456");
        
        assertEquals("janeDoe", user.getUsername());
        assertEquals("SecurePassword456", user.getPassword());
    }
}
