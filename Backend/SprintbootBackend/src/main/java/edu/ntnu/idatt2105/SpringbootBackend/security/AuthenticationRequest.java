package edu.ntnu.idatt2105.SpringbootBackend.security;

import lombok.*;

/**
 * Data transfer object for authentication requests.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
