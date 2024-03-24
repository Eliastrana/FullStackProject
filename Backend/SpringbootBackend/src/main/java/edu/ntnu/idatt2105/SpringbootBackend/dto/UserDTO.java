package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.controller.UserController;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * A Data Transfer Object for user login.
 * It encapsulates the login credentials
 * of a user, including the username and password.
 * This DTO is used as the request
 * body in the login endpoint of {@link UserController}.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @see UserController#loginUser(UserDTO)
 * @since 0.1
 * @version 0.1
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
     * @param username the username of the user attempting to log in.
     * @param password the password of the user attempting to log in.
     */
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
