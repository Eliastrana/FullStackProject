package edu.ntnu.idatt2105.SpringbootBackend.security;

import lombok.*;

/**
 * Represents the request body for authentication requests.
 * This class is used to encapsulate
 * the necessary credentials (username and password) for authenticating a user.
 * <p>
 * It serves as the input data transfer object for the login process, where the provided credentials
 * are validated against the stored user information to grant access.
 * </p>
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @since 0.1
 * @version 0.1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    /**
     * The username provided by the user for authentication.
     */
    @NonNull
    private String username;

    /**
     * The password provided by the user for authentication.
     */
    @NonNull
    private String password;
}
