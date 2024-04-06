package edu.ntnu.idatt2105.SpringbootBackend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Controller for managing user roles within the application. It supports operations such as retrieving user roles,
 * assigning new roles to users, updating existing roles, and removing roles from users.
 * This controller ensures that only authenticated users can perform these operations.
 */
@RestController
@RequestMapping("/api/user-roles")
@Tag(name = "User Role Management", description = "API for managing user roles")
public class UserRoleController {
    private final UserRoleService userRoleService;
    private final UserRepository userRepository;

    public UserRoleController(UserRoleService userRoleService, UserRepository userRepository) {
        this.userRoleService = userRoleService;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the roles associated with a specific user identified by their username.
     * This method looks up the user in the system and returns their roles if the user exists.
     *
     * @param username The username of the user whose roles are to be retrieved.
     * @return A {@link ResponseEntity} containing the list of roles if the user is found(200: Roles found),
     * or a bad request response if the user does not exist(400: User not found or bad request).
     */
    @Operation(summary = "Get User Roles", description = "Retrieve roles for a specified user")
    @ApiResponse(responseCode = "200", description = "Roles found", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "User not found or bad request")
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserRoles(@RequestParam String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }
        return ResponseEntity.ok(user.getUserRoles());
    }

    /**
     * Assigns a new role to a user identified by their username.
     * The role is specified by a role name.
     * The method checks if the assignment is possible and performs the operation if valid.
     *
     * @param username The username of the user to whom the role is to be assigned.
     * @param role The name of the role to assign to the user.
     * @return A {@link ResponseEntity} indicating success if the role is assigned(200:Role assigned successfully),
     * or a bad request response if the assignment could not be completed(400:Could not assign role to user or bad request).
     */
    @Operation(summary = "Assign Role to User", description = "Assign a new role to a specified user")
    @ApiResponse(responseCode = "200", description = "Role assigned successfully")
    @ApiResponse(responseCode = "400", description = "Could not assign role to user or bad request")
    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> assignRoleToUser(@RequestParam String username, @RequestParam String role) {
        boolean isAssigned = userRoleService.assignRoleToUser(username, role);
        if (isAssigned) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Could not assign role to user.");
        }
    }

    /**
     * Updates the role for a user identified by their username.
     * The new role is specified by the role name.
     * This method allows for changing a user's role to a different one if valid and possible.
     *
     * @param username The username of the user whose role is to be updated.
     * @param role The new role to be assigned to the user.
     * @return A {@link ResponseEntity} indicating success if the role is updated(200:Role updated successfully),
     * or a bad request response if the update could not be completed(400:Could not update role for user or bad request).
     */
    @Operation(summary = "Update Role for User", description = "Update the role for a specified user")
    @ApiResponse(responseCode = "200", description = "Role updated successfully")
    @ApiResponse(responseCode = "400", description = "Could not update role for user or bad request")
    @PutMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateRoleForUser(@RequestParam String username, @RequestParam String role) {
        if (username == null || role == null || role.isEmpty() || username.isEmpty()) {
            return ResponseEntity.badRequest().body("User ID and Role ID must be provided.");
        }
        User user = userRepository.findByUsername(username).orElse(null);

        boolean isAdmin = user.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_OWNER"));

        if (isAdmin) {
            return ResponseEntity.badRequest().body("Admins cannot be assigned roles.");
        }

        boolean isAssigned = userRoleService.updateRoleForUser(username, role);
        if (isAssigned) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Could not assign role to user.");
        }
    }

    /**
     * Removes a role from a user identified by their username. The role to be removed is specified by the role name.
     * This method allows for the removal of a user's role if valid and possible.
     *
     * @param username The username of the user from whom the role is to be removed.
     * @param role The name of the role to be removed from the user.
     * @return A {@link ResponseEntity} indicating success if the role is removed(200:Role removed successfully),
     * or a bad request response if the removal could not be completed (400:Could not remove role from user or bad request).
     */
    @Operation(summary = "Remove Role from User", description = "Remove a role from a specified user")
    @ApiResponse(responseCode = "200", description = "Role removed successfully")
    @ApiResponse(responseCode = "400", description = "Could not remove role from user or bad request")
    @DeleteMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> removeRoleFromUser(@RequestParam String username, @RequestParam String role) {
        if (username == null || role == null || role.isEmpty() || username.isEmpty()) {
            return ResponseEntity.badRequest().body("User ID and Role ID must be provided.");
        }
        User user = userRepository.findByUsername(username).orElse(null);

        boolean isAdmin = user.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_OWNER"));

        if (isAdmin) {
            return ResponseEntity.badRequest().body("Admins cannot be assigned roles.");
        }

        boolean isAssigned = userRoleService.removeRoleFromUser(username, role);
        if (isAssigned) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Could not assign role to user.");
        }
    }
}
