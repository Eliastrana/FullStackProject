package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.Data;

/**
 * Data transfer object for creating a new user.
 */

@Data
public class UserDTO {
    private String username;
    private String password;

    /**
     * Constructs a new UserDTO with specified username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
