package edu.ntnu.idatt2105.SpringbootBackend.service;

import edu.ntnu.idatt2105.SpringbootBackend.exception.NotOwnerException;
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

import java.util.Optional;


@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

@Transactional
public boolean assignRoleToUser(String username, String roleName) {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

    boolean isOwner = user.getUserRoles().stream()
            .anyMatch(ur -> ur.getRole().getRole().equals("ROLE_OWNER"));
    if (!isOwner) {
        throw new NotOwnerException("User is not an owner: " + username);
    }

    String qualifiedRoleName = "ROLE_" + roleName.toUpperCase();
    Role role = roleRepository.findByRole(qualifiedRoleName)
            .orElseThrow(() -> new RoleNotFoundException("Role not found: " + qualifiedRoleName));

    boolean alreadyHasRole = user.getUserRoles().stream()
                                .anyMatch(ur -> ur.getRole().equals(role));
    if (alreadyHasRole) {
        throw new RoleAlreadyAssignedException("User already has role: " + roleName);
    }

    // Create and save the UserRole association
    UserRole userRole = new UserRole();
    userRole.setUser(user);
    userRole.setRole(role);
    userRoleRepository.save(userRole);
    return true;
}



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

    @Transactional
    public boolean removeRoleFromUser(String username, String roleName) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return false;

        String qualifiedRoleName = "ROLE_" + roleName.toUpperCase();
        Role role = roleRepository.findByRole(qualifiedRoleName).orElse(null);
        if (role == null) return false;

        Optional<UserRole> userRoleOptional = user.getUserRoles().stream()
            .filter(ur -> ur.getRole().equals(role))
            .findFirst();

        userRoleOptional.ifPresent(userRole -> {
            user.getUserRoles().remove(userRole);
            userRoleRepository.delete(userRole);
        });

        return userRoleOptional.isPresent();
    }
}
