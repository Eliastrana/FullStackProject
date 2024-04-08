package edu.ntnu.idatt2105.SpringbootBackend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ntnu.idatt2105.SpringbootBackend.model.Role;


/**
 * The {@code RoleRepository} interface extends the Spring Data {@link JpaRepository} to provide
 * an abstraction layer for performing CRUD operations on {@link Role} entities. It serves as a
 * key component in managing roles within the application, facilitating the assignment, modification,
 * and querying of roles assigned to users.
 * <p>
 * By leveraging the capabilities of this repository, the application can effectively maintain
 * the roles of users, ensuring proper access control and authorization. The {@code findByRole}
 * method, in particular, allows for the retrieval of a {@link Role} entity based on its role name,
 * supporting dynamic access control based on role-based permissions.
 * <p>
 * This repository thus plays a crucial role in the application's security framework, enabling
 * robust and flexible management of user roles and permissions.
 *
 * @author Vegard Johnsen
 * @version 0.1
 * @since 0.1
 * @see Role for the entity managed by this repository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    /**
     * Finds a {@link Role} entity by its role name.
     * This method allows for the retrieval of role entities based on their name, facilitating
     * the identification and assignment of roles to users within the system.
     *
     * @param role The name of the role to retrieve.
     * @return An {@link Optional} containing the {@link Role} entity if found, or an empty
     *         {@link Optional} if no role with the specified name exists.
     */
    List<Role> findByRole(String role);
}
