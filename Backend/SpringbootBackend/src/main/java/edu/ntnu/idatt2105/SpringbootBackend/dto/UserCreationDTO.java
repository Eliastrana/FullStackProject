package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.controller.UserController;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object (DTO) for user registration.
 * Contains the necessary information to create a new user, including username, password, and email.
 * Used in the registration process handled by {@link UserController}.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
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
     * Constructs a new UserCreationDTO with the specified username, password, and email.
     *
     * @param username the username of the new user.
     * @param password the password of the new user.
     * @param email the email address of the new user.
     */
    public UserCreationDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
