package edu.ntnu.idatt2105.SpringbootBackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.UUID;

/**
 * Represents a role within the system, which is used to grant authorities to users. This class
 * implements {@link GrantedAuthority} to integrate with Spring Security, allowing for role-based
 * access control. Roles define the permissions or access levels that users possess, enabling
 * differentiated access and functionality within the application based on the user's role.
 * The {@code Role} class also establishes a relationship with the {@link UserRole} class to
 * associate users with their respective roles. This many-to-one relationship with {@code UserRole}
 * ensures that a single role can be assigned to multiple users, thereby categorizing users into
 * different access levels or groups based on their roles.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see GrantedAuthority
 * @see UserRole
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "userRoles")
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    /**
     * Unique identifier for the role, automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the role, representing the authority or access level it grants.
     */
    @Column(nullable = false, unique = true)
    private String role;

    /**
     * A set of {@link UserRole} associations that link this role to its users.
     * This enables the mapping of users to their roles, facilitating role-based
     * access control within the system.
     */
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;

    /**
     * Returns the name of this role, fulfilling the {@link GrantedAuthority} interface
     * requirement. This method is used by Spring Security to determine the authorities
     * granted to the user.
     *
     * @return The name of the role.
     */
    @Override
    public String getAuthority() {
        return role;
    }
}
