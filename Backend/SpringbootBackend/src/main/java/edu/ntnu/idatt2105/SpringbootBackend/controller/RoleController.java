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

@Tag(name = "Role Management", description = "API for managing roles")
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Get all roles", description = "Fetch a list of all roles", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list", content = @Content(mediaType = "application/json", 
        schema = @Schema(implementation = Role.class)))})
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

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