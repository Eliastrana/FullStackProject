package edu.ntnu.idatt2105.SpringbootBackend.controller;

import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;




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


    @Operation(summary = "Assign Role to User", description = "Assign a new role to a specified user", responses = {
        @ApiResponse(responseCode = "200", description = "Role assigned successfully"),
        @ApiResponse(responseCode = "400", description = "Could not assign role to user or bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    }, security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/")
    public ResponseEntity<?> assignRoleToUser(
        @Parameter(description = "Username of the user", required = true) @RequestParam String username, 
        @Parameter(description = "Role to assign", required = true) @RequestParam String role) {
            
        boolean isAssigned = userRoleService.assignRoleToUser(username, role);
        if (isAssigned) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Could not assign role to user.");
        }
    }

    @Operation(summary = "Update User Role", description = "Updates the role of an existing user", responses = {
        @ApiResponse(responseCode = "200", description = "Role updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateRoleForUser(
        @Parameter(description = "Username of the user", required = true) @RequestParam String username, 
        @Parameter(description = "Role to assign", required = true) @RequestParam String role) {        if (username == null || role == null || role.isEmpty() || username.isEmpty()) {
            return ResponseEntity.badRequest().body("User ID and Role ID must be provided.");
        }
        User user = userRepository.findByUsername(username).orElse(null);

        boolean isAdmin = user.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

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

    @GetMapping("/hasRole")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> userHasRole(@RequestParam String username, @RequestParam String role) {
        boolean hasRole = userRoleService.userHasRole(username, role);
        return ResponseEntity.ok(hasRole);
    }
}
