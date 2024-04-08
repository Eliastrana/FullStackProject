package edu.ntnu.idatt2105.SpringbootBackend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import edu.ntnu.idatt2105.SpringbootBackend.model.Role;
import edu.ntnu.idatt2105.SpringbootBackend.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * The {@link RoleController} class provides REST APIs for managing roles within the system.
 * It allows fetching all roles and creating new roles, leveraging the {@link RoleService}.
 * Access to the APIs is secured, requiring authentication.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see RoleService
 * @see Role
 * @see ResponseEntity
 */
@Tag(name = "Role Management", description = "API for managing roles")
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    /**
     * Constructs a new a RoleController with the provided {@link RoleService}.
     *
     * @param roleService The service layer handling role operations.
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Retrieves a list of all roles available in the system.
     * This endpoint allows clients to understand what roles are available
     * and can be assigned to users.
     *
     * @return A {@link ResponseEntity} object containing a list of all {@link Role} objects.
     * The HTTP status code is set to OK (200) indicating the request was successfully processed.
     */
    @Operation(summary = "Get all roles", description = "Fetch a list of all roles", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Role.class)))})
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    /**
     * Creates a new role in the system.
     * This endpoint allows clients to create new roles that can be assigned to users.
     *
     * @param role The role object to be created.
     * @return A {@link ResponseEntity} object containing the created {@link Role} object.
     * The HTTP status code is set to OK (200:Successfully created new role) indicating the request was successfully processed.
     */
    @Operation(summary = "Create a new role", description = "Create a new role in the system", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully created new role", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))),
        @ApiResponse(responseCode = "400", description = "Invalid role data provided")})
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role savedRole = roleService.createRole(role);
        return ResponseEntity.ok(savedRole);
    }
}