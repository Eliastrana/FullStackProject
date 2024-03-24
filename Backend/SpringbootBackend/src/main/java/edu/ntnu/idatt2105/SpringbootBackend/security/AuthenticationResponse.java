package edu.ntnu.idatt2105.SpringbootBackend.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response body for authentication requests.
 * This class is used to encapsulate
 * the JWT token generated upon successful authentication.
 * <p>
 * It serves as the output data transfer object for the login process, providing the client
 * with a JWT token that can be used for authorization in subsequent requests.
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
public class AuthenticationResponse {

    /**
     * The JWT token generated for the authenticated session.
     * This token should be used in the
     * Authorization header for later requests requiring authentication.
     */
    private String token;
}
