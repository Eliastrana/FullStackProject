package edu.ntnu.idatt2105.SpringbootBackend.exception;


/**
 * Exception indicating that a specified role was not found in the system.
 * This exception is thrown when an operation is attempted on a role
 * that does not exist, typically during role assignment or role-based access control.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 *
 */
public class RoleNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code RoleNotFoundException} with a message indicating
     * the role that was not found.
     *
     * @param roleName The name of the role that was not found.
     */
    public RoleNotFoundException(String roleName) {
        super(String.format("Role not found: %s", roleName));
    }
}
