package edu.ntnu.idatt2105.SpringbootBackend.security;

import org.junit.jupiter.api.Test;

import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthenticationRequestTest {

    @Test
    public void testBuilder() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("testUser")
                .password("testPassword")
                .build();

        assertNotNull(request);
        assertEquals("testUser", request.getUsername());
        assertEquals("testPassword", request.getPassword());
    }

    @Test
    public void testNoArgsConstructor() {
        AuthenticationRequest request = new AuthenticationRequest();

        assertNotNull(request);
    }

    @Test
    public void testAllArgsConstructor() {
        AuthenticationRequest request = new AuthenticationRequest("testUser", "testPassword");

        assertNotNull(request);
        assertEquals("testUser", request.getUsername());
        assertEquals("testPassword", request.getPassword());
    }
}