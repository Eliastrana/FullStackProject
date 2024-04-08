package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.model.Role;
import edu.ntnu.idatt2105.SpringbootBackend.repository.RoleRepository;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * The {@code RoleService} class is responsible for managing roles within the system.
 * It provides functionality to retrieve all roles, create new roles, and initialize default roles
 * in the database. This service interacts with the {@link RoleRepository} to perform its operations.
 *
 * @author Vegard Johnsen, sander rom skofsrud
 * @version 1.0
 * @since 1.0
 * @see Role
 * @see RoleRepository
 */
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    /**
     * Constructs a new {@code RoleService} with the necessary {@link RoleRepository}.
     *
     * @param roleRepository The repository used for role operations.
     */
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves all roles present in the system.
     *
     * @return A list of {@link Role} representing all roles in the system.
     */
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * Creates and saves a new role in the system.
     *
     * @param role The {@link Role} object to be created and saved.
     * @return The saved {@link Role} object.
     */
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

    /**
     * Initializes default roles in the system. This method is called after the
     * {@code RoleService} bean is fully constructed and ensures that a predefined
     * list of roles exist in the system. If a role does not exist, it is created.
     */
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
