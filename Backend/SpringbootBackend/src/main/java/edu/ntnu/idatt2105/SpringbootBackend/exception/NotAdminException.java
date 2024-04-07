package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception indicating that the current user does not have ownership rights required for a specific operation.
 * This exception is typically thrown when an operation requiring ownership, such as editing or deleting certain
 * resources, is attempted by a user who is not recognized as the owner of those resources.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see edu.ntnu.idatt2105.SpringbootBackend.controller.UserController
 *
 */
public class NotAdminException extends RuntimeException {
/**
     * Constructs a new {@code NotAdminException} with a detailed message indicating the user and the lack of ownership.
     *
     * @param username The username of the user who attempted the operation without having ownership rights.
     */
    public NotAdminException(String username) {
        super(String.format("User is not an owner: %s", username));
    }
}
