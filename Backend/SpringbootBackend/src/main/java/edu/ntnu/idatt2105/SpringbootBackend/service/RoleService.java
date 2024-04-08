package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.model.Role;
import edu.ntnu.idatt2105.SpringbootBackend.repository.RoleRepository;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role createRole(Role role) {
        // Add validation or business logic as necessary
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        // Check if the role already exists
        List<Role> existingRole = roleRepository.findByRole(role.getRole());
        for (Role r : existingRole) {
            if (r.getRole().equals(role.getRole())) {
                throw new IllegalArgumentException("Role already exists");
            }
        }
        return roleRepository.save(role);
    }

    @PostConstruct
    public void initRoles() {
        // List of default roles you want to ensure exist in your database
        List<String> defaultRoles = Arrays.asList("USER", "ADMIN");

        defaultRoles.forEach(roleName -> {
            // Check if the role already exists to avoid creating duplicates
            String roleWithPrefix = "ROLE_" + roleName; // Ensure the role name follows your naming convention
            roleRepository.findByRole(roleWithPrefix).stream().findFirst().orElseGet(() -> {
                // Role doesn't exist, so create and save it
                Role role = new Role();
                role.setRole(roleWithPrefix);
                return createRole(role);
            });
        });
    }
}
