package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.controller.UserController;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * A Data Transfer Object for user registration.
 * It encapsulates the user details
 * required for registering a new user, including username, password, and email.
 * This DTO is used as the request body in the registration endpoint of {@link UserController}.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @see UserController#registerUser(UserCreationDTO)
 * @since 0.1
 * @version 0.1
 */
@Schema(description = "User Creation DTO")
@NoArgsConstructor
@Data
public class UserCreationDTO {
    @Schema(required = true, example = "johnDoe", description = "Username of the new user")
    private String username;

    @Schema(required = true, example = "Password123", description = "Password of the new user")
    private String password;

    @Schema(required = true, example = "john.doe@example.com", description = "Email of the new user")
    private String email;

    /**
     * Creates a new instance of UserCreationDTO with the specified details.
     *
     * @param username the username for the new user.
     * @param password the password for the new user.
     * @param email    the email address for the new user.
     */
    public UserCreationDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
