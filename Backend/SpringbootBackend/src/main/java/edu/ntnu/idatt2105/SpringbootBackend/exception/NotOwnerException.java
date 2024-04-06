package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception indicating that the current user does not have ownership rights required for a specific operation.
 * This exception is typically thrown when an operation requiring ownership, such as editing or deleting certain
 * resources, is attempted by a user who is not recognized as the owner of those resources.
 *
 * @author Vegard Johnsen
 *
 */
public class NotOwnerException extends RuntimeException {

    /**
     * Constructs a new {@code NotOwnerException} with a detailed message indicating the user and the lack of ownership.
     *
     * @param username The username of the user who attempted the operation without having ownership rights.
     */
    public NotOwnerException(String username) {
        super(String.format("User is not an owner: %s", username));
    }
}
