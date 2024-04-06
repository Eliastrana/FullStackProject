package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String roleName) {
        super(String.format("Role not found: %s", roleName));
    }
}
