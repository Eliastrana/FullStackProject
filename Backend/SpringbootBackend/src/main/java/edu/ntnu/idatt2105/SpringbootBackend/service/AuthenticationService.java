package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserCreationDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.UserAlreadyExistException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Role;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.model.UserRole;
import edu.ntnu.idatt2105.SpringbootBackend.repository.RoleRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationRequest;
import edu.ntnu.idatt2105.SpringbootBackend.security.AuthenticationResponse;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * Provides services for user authentication, including registration and login.
 * Utilizes {@link UserRepository} for user persistence, {@link PasswordEncoder} for password encryption,
 * and {@link JWTService} for JWT token generation upon successful authentication.
 *
 * @author Vegard Johnsen
 * @see UserCreationDTO
 * @see AuthenticationResponse
 * @see UserAlreadyExistException
 * @since 0.1
 * @version 0.1
 */
@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

    /**
     * Registers a new user with the given user details.
     * If the username already exists, it throws
     * a {@link UserAlreadyExistException}.
     * Upon successful registration, a JWT token is generated for the user.
     *
     * @param userCreationDTO Object containing the new user's details.
     * @return An {@link AuthenticationResponse} containing the newly generated JWT token.
     * @throws UserAlreadyExistException If a user with the given username already exists.
     */
    @Transactional
    public AuthenticationResponse register(UserCreationDTO userCreationDTO) {

        if (userRepository.findByUsername(userCreationDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("Username already exists");
        }

        if (!pattern.matcher(userCreationDTO.getPassword()).matches()) {
            throw new IllegalArgumentException("Password does not meet complexity requirements.");
        }

        User user = User
                .builder()
                .username(userCreationDTO.getUsername())
                .password(passwordEncoder.encode(userCreationDTO.getPassword()))
                .email(userCreationDTO.getEmail())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        logger.info("Registering user with username: " + user.getUsername());

        List<Role> defaultRole = roleRepository.findByRole("ROLE_USER");
        Role role = defaultRole.get(0);


        logger.info("Default role is: " + role.getRole());

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        logger.info("User role is: " + userRole.getUser().getUsername());
        userRole.setRole(role);
        logger.info("Role is: " + userRole.getRole().getRole());

        if (user.getUserRoles() == null) {
            logger.info("User roles are null");
            user.setUserRoles(new HashSet<>());
        }
        logger.info("User roles are: " + user.getUserRoles());
        user.getUserRoles().add(userRole);
        logger.info("User roles are: " + user.getUserRoles());


        //user.getUserRoles().add(userRole);

        User savedUser = userRepository.save(user);
        logger.info("User " + savedUser.getUsername() + " has been registered");

        // Generate JWT token
        String jwtToken = jwtService.generateToken(savedUser);
        logger.info("Their JWT is: " + jwtToken);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticates a user with the provided username and password.
     * If successful, generates and returns a JWT token in an {@link AuthenticationResponse}.
     *
     * @param request An {@link AuthenticationRequest} containing login credentials.
     * @return An {@link AuthenticationResponse} containing a JWT token for the authenticated user.
     * @throws UsernameNotFoundException If the username does not exist in the database.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.info("Authenticating user information for: " + request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.getUsername()));

        String jwtToken = jwtService.generateToken(user);
        logger.info("User " + request.getUsername() + " has been authenticated");

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
