package edu.ntnu.idatt2105.SpringbootBackend.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsDTOTest {

    @Test
    void testBuilderPattern() {
        UUID id = UUID.randomUUID();
        String username = "johnDoe";
        String email = "john.doe@example.com";

        UserDetailsDTO userDetails = UserDetailsDTO.builder()
                .id(id)
                .username(username)
                .email(email)
                .build();

        assertEquals(id, userDetails.getId());
        assertEquals(username, userDetails.getUsername());
        assertEquals(email, userDetails.getEmail());
    }
    @Test
    void testWithEdgeCaseValues() {
        UUID id = UUID.randomUUID();
        String longUsername = "user" + "name".repeat(50); // Create a long username string
        String specialCharEmail = "email+test@example.com"; // Email with special characters

        UserDetailsDTO userDetails = UserDetailsDTO.builder()
                .id(id)
                .username(longUsername)
                .email(specialCharEmail)
                .build();

        assertEquals(id, userDetails.getId());
        assertEquals(longUsername, userDetails.getUsername());
        assertEquals(specialCharEmail, userDetails.getEmail());
    }

    @Test
    void testHandlingOfNullValues() {
        UserDetailsDTO userDetails = UserDetailsDTO.builder()
                .id(null)
                .username(null)
                .email(null)
                .build();

        assertNull(userDetails.getId());
        assertNull(userDetails.getUsername());
        assertNull(userDetails.getEmail());
    }
}
