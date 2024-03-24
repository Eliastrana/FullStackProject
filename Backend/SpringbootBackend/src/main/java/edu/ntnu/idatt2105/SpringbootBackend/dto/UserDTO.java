package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.controller.UserController;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for user login.
 * Contains the necessary information for user authentication, including username and password.
 * Used in the login process handled by {@link UserController}.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 */
@Schema(description = "User DTO for login")
@Data
public class UserDTO {
    @Schema(required = true, example = "johnDoe", description = "Username of the user")
    private String username;

    @Schema(required = true, example = "Password123", description = "Password of the user")
    private String password;

    /**
     * Constructs a new UserDTO with the specified username and password for login purposes.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     */
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
