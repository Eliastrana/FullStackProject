package edu.ntnu.idatt2105.SpringbootBackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    private JWTService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JWTService();
    }

    @Test
    void generateToken_ShouldGenerateNotNullToken() {
        User userDetails = new User("user", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtService.generateToken(userDetails);
        assertThat(token).isNotNull();
    }

    @Test
    void isTokenValid_ShouldReturnTrueForValidToken() {
        User userDetails = new User("user", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void isTokenExpired_ShouldReturnFalseForNewlyGeneratedToken() {
        User userDetails = new User("user", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtService.generateToken(userDetails);
        boolean isExpired = jwtService.isTokenExpired(token);
        assertFalse(isExpired);
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        User userDetails = new User("user", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertThat(username).isEqualTo("user");
    }

    @Test
    void extractExpiration_ShouldReturnFutureDate() {
        User userDetails = new User("user", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        String token = jwtService.generateToken(userDetails);
        Date expirationDate = jwtService.extractExpiration(token);
        assertThat(expirationDate).isAfter(new Date());
    }

}
