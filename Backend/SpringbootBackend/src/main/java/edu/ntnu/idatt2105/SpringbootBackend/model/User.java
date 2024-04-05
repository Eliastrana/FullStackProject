package edu.ntnu.idatt2105.SpringbootBackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents a user in the system with all necessary user details.
 * This entity is used for authentication and authorization processes, as well as storing user-related information
 * like usernames, passwords, and email addresses.
 * Implements {@link UserDetails} to integrate with Spring Security
 * for authentication and authorization functionalities.
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @see UserDetails
 * @since 0.1
 * @version 0.1
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    /**
     * The unique identifier for the user. Automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The username of the user. Must be unique across all users.
     */
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /**
     * The password of the user, stored in a secured format.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * The email address of the user. Must be unique across all users.
     */
    @Email(message = "Email should be valid")
    private String email;

    /**
     * The first name of the user.
     */
    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired;

    /**
     * The user's account locking status.
     */

    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked;

    /**
     * The user's credentials expiration status.
     */

    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired;

    /**
     * The user's enabled status.
     */

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;


    /**
     * Retrieves the authorities granted to the user.
     *
     * @return A collection of {@link GrantedAuthority} objects associated with the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream()
                        .map(userRole -> userRole.getRole())
                        .collect(Collectors.toSet());
    }

    /**
     * Retrieves the password used to authenticate the user.
     *
     * @return The user's password.
     */
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired; 
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return True if the user's account is valid, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked; // Placeholder for actual account locking logic
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return True if the user's credentials are valid, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return True if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}