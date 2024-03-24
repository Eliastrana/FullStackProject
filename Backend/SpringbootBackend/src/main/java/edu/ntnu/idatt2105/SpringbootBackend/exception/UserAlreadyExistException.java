package edu.ntnu.idatt2105.SpringbootBackend.exception;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserCreationDTO;
import edu.ntnu.idatt2105.SpringbootBackend.service.AuthenticationService;

/**
 * Exception thrown when an attempt is made to register a user with a username that already exists in the system.
 * This exception is typically handled during the user registration process to inform the client that the chosen
 * username is not available.
 *
 * @author Vegard Johnsen
 * @see AuthenticationService#register(UserCreationDTO)
 * @since 0.1
 * @version 0.1
 */
public class UserAlreadyExistException extends IllegalArgumentException {

    /**
     * Constructs a {@code UserAlreadyExistException} with the detail message constructed from the specified username.
     * The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     *
     * @param username the username that already exists
     */
    public UserAlreadyExistException(String username) {
        super(String.format("User '%s' already exists", username));
    }
}
