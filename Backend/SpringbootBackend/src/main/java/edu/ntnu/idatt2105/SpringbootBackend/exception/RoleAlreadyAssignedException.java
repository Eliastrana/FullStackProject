package edu.ntnu.idatt2105.SpringbootBackend.exception;

public class RoleAlreadyAssignedException extends RuntimeException {
    public RoleAlreadyAssignedException(String roleName) {
        super(String.format("User already has role: %s", roleName));
    }
}
