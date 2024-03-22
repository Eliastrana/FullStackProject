package edu.ntnu.idatt2105.SpringbootBackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data transfer object for creating a new user.
 */


@NoArgsConstructor
@Data
public class UserCreationDTO {
    private String username;
    private String password;
    private String email;

    /**
     * Constructs a new UserCreationDTO with specified username, password, and email.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email The email of the user.
     */
    public UserCreationDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
