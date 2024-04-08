package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.PasswordUpdateDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDetailsDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.PasswordDoesNotMeetRequirements;
import edu.ntnu.idatt2105.SpringbootBackend.exception.UserNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service class for managing user-related operations.
 * It provides functionality to search for users based on their DTO representation and to load user details by username.
 * This class leverages the {@link UserRepository} for persistence operations and {@link UserMapper} for DTO-entity conversion.
 *
 * @author Vegard Johnsen,
 * @see UserDTO
 * @see UserNotFoundException
 * @since 0.1
 * @version 0.1
 */
@RequiredArgsConstructor
@Service
public class UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";    
    private static final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

    /**
     * Finds a user based on the provided {@link UserDTO}.
     *
     * @param userDTO The {@link UserDTO} containing user identification details.
     * @return A {@link UserDTO} with full user details.
     * @throws UserNotFoundException if the user cannot be found.
     */
    public UserDTO findUser(UserDTO userDTO) {
        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());
        user.ifPresent(Objects::requireNonNull);
        return userMapper.toUserDTO(user
                .orElseThrow(() -> new UserNotFoundException("User not found, username: " + userDTO.getUsername())));
    }

    /**
     * Loads a user's details based on their username.
     *
     * @param username The username of the user to load.
     * @return A {@link User} entity with user details.
     * @throws UserNotFoundException if the user cannot be found.
     */
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found, username: " + username));
    }

    /**
     * Retrieves the user details based on the provided username.
     * This method is used for loading user details for authentication and authorization purposes.
     * It returns a {@link UserDetailsDTO} object containing the user's identification details.
     * @param username The username of the user to retrieve details for.
     * @return A {@link UserDetailsDTO} object containing the user's identification details.
     */
    public UserDetailsDTO getUserDetails(String username) {
        return userRepository.findByUsername(username)
                             .map(userMapper::toUserDetails)
                             .orElse(null); // Or handle the absence of the user differently
    }

    /**
     * Retrived the user detials of all users in the database.
     * This method is used for loading user details for admin purposes.
     * It returns a List of {@link UserDetailsDTO} object containing the user's identification details.
     * @return A List of {@@link UserDetailsDTO} of all users
     */
    public Iterable<UserDetailsDTO> getAllUsers() {
    Iterable<User> users = userRepository.findAll();

        return StreamSupport.stream(users.spliterator(), false)
                                                         .map(user -> userMapper.toUserDetails(user))
                                                         .collect(Collectors.toList());
    }

    @Transactional
    public boolean deleteUser(String username) {
        boolean userExists = userRepository.findByUsername(username).isPresent();
        if (!userExists) {
            return false;
        }
        userRepository.deleteByUsername(username);
        return true;
    }

    public void updatePassword(String username, PasswordUpdateDTO passwordUpdateDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(!pattern.matcher(passwordUpdateDTO.getNewPassword()).matches()) {
            throw new PasswordDoesNotMeetRequirements(passwordUpdateDTO.getNewPassword());
        }
        if(passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Old password is incorrect.");
        }
    }

    }