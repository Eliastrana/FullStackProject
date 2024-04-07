package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDetailsDTO;
import edu.ntnu.idatt2105.SpringbootBackend.exception.UserNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    
    List<UserDetailsDTO> userDetailsDTOs = StreamSupport.stream(users.spliterator(), false)
                                                         .map(user -> userMapper.toUserDetails(user))
                                                         .collect(Collectors.toList());
    
    return userDetailsDTOs;
}
}