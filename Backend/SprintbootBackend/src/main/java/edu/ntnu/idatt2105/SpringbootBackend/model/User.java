package edu.ntnu.idatt2105.SpringbootBackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

/**
 * Represents a user in the system.
 * Implements Spring Security UserDetails to enable authentication and authorization.

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
     * The unique identifier of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The username of the user.
     */

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /**
     * The password of the user.
     */

    @Column(name = "password", nullable = false)
    private String password;

    /**
     * The email of the user.
     */


    @Column(name = "email", unique = true, nullable = false)
    private String email;

    //TODO: Add  @OneToMany(mappedBy = "user") etc


    /**
     * Retrieves the authorities granted to the user.
     *
     * @return The authorities granted to the user.
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return True if the user's account is valid, false otherwise.
     */

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     *
     * @return True if the user is not locked, false otherwise.
     */

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired.
     *
     * @return True if the user's credentials are valid, false otherwise.
     */

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     *
     * @return True if the user is enabled, false otherwise.
     */

    @Override
    public boolean isEnabled() {

        return true;
    }

}




