package edu.ntnu.idatt2105.SpringbootBackend.dto;

import java.util.UUID;

import edu.ntnu.idatt2105.SpringbootBackend.controller.UserController;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * A Data Transfer Object for user details.
 * It encapsulates the user details
 * required for getting details of a user, including username and email.
 * This DTO is used as the response body in the get user details endpoint of {@link UserController}.
 *
 * @author Vegard Johnsen
 * @since 0.1
 * @version 0.1
 * @see UserController
 */

@Schema(description = "User DTO for getting details of a user")
@Data
@Builder
public class UserDetailsDTO {
    /**
     * The unique identifier of the user, which serves as a primary key for user-related operations.
     */
    @Schema(required = true, example = "123e4567-e89b-12d3-a456-426614174000", description = "The unique identifier of the user")
    private UUID id;
    /**
     * The username of the user, which serves as a unique identifier for the user within the system.
     */
    @Schema(required = true, example = "johnDoe", description = "Username of the user")
    private String username;
    /**
     * The email of the user, which is used for communication and account verification.
     */
    @Schema(required = true, example = "example@mail.com", description = "Email of the user")
    private String email;

}
