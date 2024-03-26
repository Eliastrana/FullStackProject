package edu.ntnu.idatt2105.SpringbootBackend.exception;

import edu.ntnu.idatt2105.SpringbootBackend.dto.UserDTO;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserService;

/**
 * Exception thrown
 * when an attempt is made to perform an operation on or with a user that cannot be found in the system.
 * This could occur in scenarios such as authentication,
 * user profile updates, or other actions that require a valid user reference.
 *
 * @author Vegard Johnsen
 * @see UserService#loadUserByUsername(String)
 * @see UserService#findUser(UserDTO)
 * @since 0.1
 * @version 0.1
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a {@code UserNotFoundException} with the detail message constructed from the specified username.
     * The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     *
     * @param username the username that cannot be found
     */
    public UserNotFoundException(String username) {
        super(String.format("User '%s' not found", username));
    }
}
