package edu.ntnu.idatt2105.SpringbootBackend.mapper;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDTO;
import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDetailsDTO;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;

import org.springframework.stereotype.Component;

/**
 * Provides functionality to map between {@link User} entities and {@link UserDTO} objects.
 * This mapper facilitates the conversion of data between the domain model layer and the data transfer object
 * (DTO) layer,
 * enabling the encapsulation of data that is transferred between client and server.
 *
 * @author Vegard Johnsen
 * @see User
 * @see UserDTO
 * @since 0.1
 * @version 0.1
 */
@Component
public class UserMapper {

    /**
     * Converts a {@link User} entity to a {@link UserDTO} object.
     * This method maps the username and email from the User entity to the UserDTO,
     * which can be used for data transfer,
     * especially in scenarios where user details need to be sent to the client.
     *
     * @param user The User entity to convert.
     * @return A new UserDTO object containing the username and email from the provided User entity.
     */
    public UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUsername(), user.getEmail());
    }

    /**
     * Converts a {@link User} entity to a {@link UserDetailsDTO} object.
     * This method maps the id, username, and email from the User entity to the UserDetailsDTO,
     * which can be used for data transfer,
     * especially in scenarios where user details need to be sent to the client.
     *
     * @param user The User entity to convert.
     * @return A new UserDetailsDTO object containing the id, username, and email from the provided User entity.
     */

    public UserDetailsDTO toUserDetails(User user) {
        if (user == null) {
            return null;
        }
        UserDetailsDTO dto = UserDetailsDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .build();

        return dto;
    }
}
