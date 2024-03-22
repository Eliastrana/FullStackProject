package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserCreationDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.UserAlreadyExistException;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
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

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Transactional
    public AuthenticationResponse register(UserCreationDTO userCreationDTO) {
        User user = User
                .builder()
                .username(userCreationDTO.getUsername())
                .password(passwordEncoder.encode(userCreationDTO.getPassword()))
                .email(userCreationDTO.getEmail())
                .build();
        if (userRepository.findByUsername(userCreationDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("Username already exists");
        }
        userRepository.save(user);
        logger.info("User with username: " + user.getUsername() + " has been registered");

        String jwtToken = jwtService.generateToken(user);
        logger.info("Their JWT is: " + jwtToken);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();


    }
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
