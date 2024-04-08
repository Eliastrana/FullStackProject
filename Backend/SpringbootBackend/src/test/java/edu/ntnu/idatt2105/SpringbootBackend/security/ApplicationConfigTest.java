package edu.ntnu.idatt2105.SpringbootBackend.security;

import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

@SpringBootTest()
@ActiveProfiles("test")
public class ApplicationConfigTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void contextLoads() {
        assertNotNull(userRepository);
        assertNotNull(userDetailsService);
        assertNotNull(authenticationProvider);
        assertNotNull(authenticationManager);
        assertNotNull(passwordEncoder);
    }

    @Test
    @DirtiesContext
    public void testUserDetailsService() {
        User user = new User();
        String username = UUID.randomUUID().toString();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("testPassword"));
        user.setEmail("test@mail.com");
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertTrue(passwordEncoder.matches("testPassword", userDetails.getPassword()));
    }

    @Test
    public void testUserDetailsServiceThrowsUsernameNotFoundException() {
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonexistentUser"));
    }

    @Test
    @DirtiesContext
    public void testAuthenticationProvider() {
        User user = new User();
        String username = UUID.randomUUID().toString();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("testPassword"));
        user.setEmail("user@mail.com");
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        userRepository.save(user);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, "testPassword");
        assertNotNull(authenticationProvider.authenticate(authentication));
    }

}