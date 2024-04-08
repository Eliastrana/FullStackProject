package edu.ntnu.idatt2105.SpringbootBackend.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthenticationResponseTest {

    @Test
    public void testBuilder() {
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("testToken")
                .build();

        assertNotNull(response);
        assertEquals("testToken", response.getToken());
    }

    @Test
    public void testNoArgsConstructor() {
        AuthenticationResponse response = new AuthenticationResponse();

        assertNotNull(response);
    }

    @Test
    public void testAllArgsConstructor() {
        AuthenticationResponse response = new AuthenticationResponse("testToken");

        assertNotNull(response);
        assertEquals("testToken", response.getToken());
    }
}
