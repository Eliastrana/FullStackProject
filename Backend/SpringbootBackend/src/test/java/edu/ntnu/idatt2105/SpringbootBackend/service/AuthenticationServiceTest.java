package edu.ntnu.idatt2105.SpringbootBackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserCreationDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.UserAlreadyExistException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Role;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.RoleRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationRequest;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        // Common setup if needed
    }
    @Test
    void register_Success() {
        // Arrange
        UserCreationDTO userCreationDTO = new UserCreationDTO("newUser", "password", "newUser@example.com");
        Role defaultRole = new Role();
        defaultRole.setId(UUID.randomUUID());
        defaultRole.setRole("USER");
        String expectedToken = "test.jwt.token";
    
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(roleRepository.findByRole("ROLE_USER")).thenReturn(List.of(defaultRole));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn(expectedToken);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
        // Act
        AuthenticationResponse response = authenticationService.register(userCreationDTO);
    
        // Assert
        assertNotNull(response);
        assertEquals(expectedToken, response.getToken());
    
        // Verify interactions
        verify(userRepository, times(1)).save(any(User.class));
    }
    


    @Test
    void register_UserAlreadyExists_ThrowsException() {
    // Arrange
    UserCreationDTO userCreationDTO = new UserCreationDTO("existingUser", "password", "existingUser@example.com");

    when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));

    // Act & Assert
    assertThrows(UserAlreadyExistException.class, () -> {
        authenticationService.register(userCreationDTO);
    });
    }

    @Test
    void authenticate_UserNotFound_ThrowsException() {
    // Arrange
    AuthenticationRequest request = new AuthenticationRequest("nonExistingUser", "password");

    when(userRepository.findByUsername("nonExistingUser")).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(UsernameNotFoundException.class, () -> {
        authenticationService.authenticate(request);
    });
    }
}