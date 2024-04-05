package edu.ntnu.idatt2105.SpringbootBackend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserRoleService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {
    private final UserRoleService userRoleService;
    private final UserRepository userRepository;

    public UserRoleController(UserRoleService userRoleService, UserRepository userRepository) {
        this.userRoleService = userRoleService;
        this.userRepository = userRepository;
    }

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> assignRoleToUser(@RequestParam String username, @RequestParam String role) {

        if (username == null || role == null || role.isEmpty() || username.isEmpty()) {
            return ResponseEntity.badRequest().body("User ID and Role ID must be provided.");
        }
        User user = userRepository.findByUsername(username).orElse(null);

        boolean isAdmin = user.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_OWNER"));

        if (isAdmin) {
            return ResponseEntity.badRequest().body("Admins cannot be assigned roles.");
        }

        boolean isAssigned = userRoleService.assignRoleToUser(username, role);
        if (isAssigned) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("Could not assign role to user.");
        }
    }

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
