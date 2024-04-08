package edu.ntnu.idatt2105.SpringbootBackend.dto;

import edu.ntnu.idatt2105.SpringbootBackend.controller.UserController;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Data
public class UserCreationDTO {
    /**
     * The username of the new user, which serves as a unique identifier for the user within the system.
     */
    @NotBlank(message = "Username is required")
    @Schema(required = true, example = "johnDoe", description = "Username of the new user")
    private String username;

    /**
     * The password of the new user, which is used for authentication and account access.
     */
    @NotBlank(message = "Password is required")
    @Schema(required = true, example = "Password123", description = "Password of the new user")
    private String password;

    /**
     * The email of the new user, which is used for communication and account verification.
     */
    @Email(message = "Email should be valid")
    @Schema(required = true, example = "john.doe@example.com", description = "Email of the new user")
    private String email;

}
