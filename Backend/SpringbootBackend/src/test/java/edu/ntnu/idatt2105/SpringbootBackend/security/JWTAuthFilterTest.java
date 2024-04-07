package edu.ntnu.idatt2105.SpringbootBackend.security;

import edu.ntnu.idatt2105.SpringbootBackend.model.Role;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.model.UserRole;
import edu.ntnu.idatt2105.SpringbootBackend.service.JWTService;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class JWTAuthFilterTest {

    @Mock
    private JWTService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JWTAuthFilter jwtAuthFilter;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

@Test
void shouldAuthenticateUserWithValidJwt() throws Exception {
    String jwt = "valid.jwt.token";
    String username = "user";
    // Create role and user with that role
    Role role = new Role();
    role.setRole("USER");
    UserRole userRole = new UserRole();
    userRole.setRole(role);
    Set<UserRole> userRoles = new HashSet<>();
    userRoles.add(userRole);

    User user = User.builder()
                    .username(username)
                    .userRoles(userRoles)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .enabled(true)
                    .build();

    when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
    when(jwtService.extractUsername(jwt)).thenReturn(username);
    when(userService.loadUserByUsername(username)).thenReturn(user);
    when(jwtService.isTokenValid(jwt, user)).thenReturn(true);

    jwtAuthFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain, times(1)).doFilter(request, response);
    verify(jwtService, times(1)).isTokenValid(jwt, user);

    // Verifying that Security Context holds the authenticated user with correct authorities
    UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)
            SecurityContextHolder.getContext().getAuthentication();
    assertNotNull(authentication);
    assertEquals(username, authentication.getName());
    assertTrue(authentication.getAuthorities().stream()
               .anyMatch(authority -> authority.getAuthority().equals("USER")));
}


    @Test
    void shouldNotAuthenticateUserWithoutJwt() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtService, never()).extractUsername(anyString());
    }

    @Test
    void shouldNotAuthenticateUserWithInvalidJwt() throws Exception {
        // Arrange
        String jwt = "invalid.jwt.token";
        String username = "user";
        User user = new User();
        user.setUsername(username);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtService.extractUsername(jwt)).thenReturn(username);
        when(userService.loadUserByUsername(username)).thenReturn(user);
        when(jwtService.isTokenValid(jwt, user)).thenReturn(false);

        // Act
        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService, times(1)).isTokenValid(jwt, user);
        verify(filterChain, times(1)).doFilter(request, response);  
        // Assert that the security context is not set since the token is invalid
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
