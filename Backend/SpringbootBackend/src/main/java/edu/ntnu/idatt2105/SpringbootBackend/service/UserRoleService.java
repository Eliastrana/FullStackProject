package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.exception.RoleAlreadyAssignedException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.UserNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.exception.RoleNotFoundException;
import edu.ntnu.idatt2105.SpringbootBackend.model.Role;
import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.model.UserRole;
import edu.ntnu.idatt2105.SpringbootBackend.repository.RoleRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing user roles.
 * It provides functionalities to assign, update, and remove roles for users, 
 * as well as checking if a user has a specific role.
 */

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * Constructs a UserRoleService with necessary repositories.
     *
     * @param userRoleRepository the repository for user-role associations
     * @param userRepository the repository for users
     * @param roleRepository the repository for roles
     */

    public UserRoleService(UserRoleRepository userRoleRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Assigns a role to a user.
     *
     * @param username the username of the user
     * @param roleName the name of the role to assign
     * @return true if the role was successfully assigned
     * @throws UserNotFoundException if the user with the given username does not exist
     * @throws RoleNotFoundException if the role does not exist
     * @throws RoleAlreadyAssignedException if the user already has the specified role
     */

    @Transactional
    public boolean assignRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        String qualifiedRoleName = "ROLE_" + roleName.toUpperCase();
        List<Role> role = roleRepository.findByRole(qualifiedRoleName);
        if (role.isEmpty()) {
            throw new RoleNotFoundException("Role not found: " + roleName);
        }

        boolean alreadyHasRole = user.getUserRoles().stream()
            .anyMatch(ur -> ur.getRole().getRole().equalsIgnoreCase(qualifiedRoleName));
        if (alreadyHasRole) {
            throw new RoleAlreadyAssignedException("User already has role: " + roleName);
        }

        // Create and save the UserRole association
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role.get(0));
        userRoleRepository.save(userRole);
        return true;
    }


    /**
     * Updates the role for a user, replacing any existing roles with the new specified role.
     *
     * @param username the username of the user
     * @param newRoleName the name of the new role to assign to the user
     * @return true if the role was successfully updated
     */

    @Transactional
    public boolean updateRoleForUser(String username, String newRoleName) {
        // Assuming you want to replace all existing roles with a new single role
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return false;

        // Remove all current roles
        userRoleRepository.deleteAllByUser(user);

        // Assign new role
        return assignRoleToUser(username, newRoleName);
    }

        /**
     * Removes a role from a user.
     *
     * @param username the username of the user
     * @param roleName the name of the role to remove
     * @return true if the role was successfully removed
     * @throws RoleNotFoundException if the role does not exist
     */

    @Transactional
    public boolean removeRoleFromUser(String username, String roleName) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return false;

        String qualifiedRoleName = "ROLE_" + roleName.toUpperCase();
        List <Role> role = roleRepository.findByRole(qualifiedRoleName);
        if (role.isEmpty()) {
            throw new RoleNotFoundException("Role not found: " + roleName);
        }
        Optional<UserRole> userRoleOptional = user.getUserRoles().stream()
            .filter(ur -> ur.getRole().equals(role.get(0)))
            .findFirst();

        userRoleOptional.ifPresent(userRole -> {
            user.getUserRoles().remove(userRole);
            userRoleRepository.delete(userRole);
        });

        return userRoleOptional.isPresent();
    }

      /**
     * Checks if a user has a specific role.
     *
     * @param username the username of the user
     * @param roleName the name of the role to check
     * @return true if the user has the specified role
     */
    
    @Transactional(readOnly = true)
    public boolean userHasRole(String username, String roleName) {
        return userRoleRepository.existsByUserUsernameAndRoleRole(username, "ROLE_" + roleName.toUpperCase());
    }
}