package edu.ntnu.idatt2105.SpringbootBackend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import edu.ntnu.idatt2105.SpringbootBackend.model.Role;
import edu.ntnu.idatt2105.SpringbootBackend.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role savedRole = roleService.createRole(role);
        return ResponseEntity.ok(savedRole);
    }
}
