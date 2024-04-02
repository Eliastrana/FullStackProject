package edu.ntnu.idatt2105.SpringbootBackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

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
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;


    /**
     * Retrieves the authorities granted to the user.
     *
     * @return A collection of {@link GrantedAuthority} objects associated with the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;  // Placeholder for actual authorities retrieval logic
    }

    /**
     * Retrieves the password used to authenticate the user.
     *
     * @return The user's password.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // Placeholder for actual account expiration logic
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return True if the user's account is valid, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // Placeholder for actual account locking logic
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     *
     * @return True if the user's credentials are valid, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Placeholder for actual credentials expiration logic
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return True if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true; // Placeholder for actual user enabling/disabling logic
    }
}
