package edu.ntnu.idatt2105.SpringbootBackend.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;


/**
 * Represents the association between a {@link User} and a {@link Role} within the system.
 * This entity is crucial for implementing role-based access control (RBAC), allowing the
 * application to assign specific roles to users and thereby grant them specific permissions
 * or access rights based on their roles.
 *
 * The {@code UserRole} class facilitates a many-to-many relationship between users and roles,
 * realized through a join table in the database. However, from a domain model perspective,
 * this class makes the association explicit by treating it as a separate entity that can
 * be extended with additional attributes if needed (e.g., assignment date, expiration date).
 *
 * Each instance of {@code UserRole} links one {@link User} to one {@link Role}, allowing for
 * flexible and granular assignment of roles to users. This model supports scenarios where a
 * user might have multiple roles and a role might be assigned to multiple users.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @version 0.1
 * @since 0.1
 * @see User for the users in the system
 * @see Role for the roles that can be assigned to users
 */
@Entity
@Table(name = "user_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "role"})
public class UserRole {

    /**
     * The unique identifier for the user-role association. This is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The user involved in this role association. This is part of a many-to-one
     * relationship where a user can have multiple roles.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * The role involved in this association. This is part of a many-to-one relationship
     * where a role can be assigned to multiple users.
     */
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

}
