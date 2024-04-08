package edu.ntnu.idatt2105.SpringbootBackend.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response to a successful authentication request. This class encapsulates
 * the JWT token that is sent back to the client upon successful authentication. The token
 * is used for authorizing subsequent requests by the client. This DTO (Data Transfer Object)
 * is part of the security layer, facilitating the transfer of crucial authentication information
 * between the server and clients.
 *
 * @author vegard johnsen
 * @version 0.1
 * @since 0.1
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    /**
     * The JWT token issued by the server upon successful authentication.
     * Clients are expected to include this token in the Authorization header
     * of their requests to access secured endpoints.
     */
    private String token;
}
