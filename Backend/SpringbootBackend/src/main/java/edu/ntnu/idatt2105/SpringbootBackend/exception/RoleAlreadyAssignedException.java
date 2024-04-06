package edu.ntnu.idatt2105.SpringbootBackend.exception;

/**
 * Exception indicating that a specified role has already been assigned to a user.
 * This exception is used to signal attempts to assign a role to a user
 * when they already possess that role, preventing duplication of roles for a single user.
 *
 * @author Vegard Johnsen
 *
 */
public class RoleAlreadyAssignedException extends RuntimeException {

    /**
     * Constructs a new {@code RoleAlreadyAssignedException} with a message indicating
     * the role that was attempted to be assigned but was already present.
     *
     * @param roleName The name of the role that was already assigned to the user.
     */
    public RoleAlreadyAssignedException(String roleName) {
        super(String.format("User already has role: %s", roleName));
    }
}
