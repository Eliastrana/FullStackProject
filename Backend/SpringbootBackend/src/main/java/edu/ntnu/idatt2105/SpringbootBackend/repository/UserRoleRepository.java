package edu.ntnu.idatt2105.SpringbootBackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.model.UserRole;

/**
 * The {@code UserRoleRepository} interface extends the {@link JpaRepository} to manage
 * {@link UserRole} entities within the database. This repository facilitates the
 * association and dissociation of roles with users, enabling dynamic access control
 * and permission management within the system.
 * <p>
 * It provides capabilities to delete all roles associated with a specific user and to
 * find a user's role by their username. These operations are crucial for maintaining
 * the integrity and security of user access levels and ensuring that users have the
 * appropriate permissions for their actions within the system.
 * <p>
 * The management of user roles through this repository supports the enforcement of
 * security policies and the customization of user experiences based on roles,
 * thereby contributing to a secure and flexible application environment.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see UserRole for the entity managed by this repository.
 * @see User for the user entity associated with roles.
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    /**
     * Deletes all roles associated with a specific user.
     * This method supports the revocation of all access and permissions from a user,
     * typically used in scenarios such as user deactivation or role reset processes.
     *
     * @param user The {@link User} entity whose roles are to be deleted.
     */
    void deleteAllByUser(User user);

    /**
     * Finds the role of a user by their username.
     * This method enables the lookup of a user's role based on their unique username,
     * facilitating access control and permission checks throughout the application.
     *
     * @param username The username of the user whose role is to be found.
     * @return An {@link Optional} containing the {@link UserRole} if found,
     * or an empty Optional if the user does not have an associated role.
     */
    Optional<UserRole> findByUserUsername(String username);
    boolean existsByUserUsernameAndRoleRole(String username, String role);
}