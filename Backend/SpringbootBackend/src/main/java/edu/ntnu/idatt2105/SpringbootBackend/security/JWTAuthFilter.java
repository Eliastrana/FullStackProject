package edu.ntnu.idatt2105.SpringbootBackend.security;

import edu.ntnu.idatt2105.SpringbootBackend.model.User;
import edu.ntnu.idatt2105.SpringbootBackend.service.JWTService;
import edu.ntnu.idatt2105.SpringbootBackend.service.UserService;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * A filter that executes once per request to authenticate users based on JWT tokens.
 * This filter intercepts the request to check for the presence of a JWT token in the Authorization header,
 * validates the token, and sets the authentication in the security context if the token is valid.
 * <p>
 * It integrates with Spring Security's filtering mechanism to provide stateless authentication
 * using JWT tokens, allowing for secure access to protected resources.
 * </p>
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @see OncePerRequestFilter
 * @see JWTService
 * @see UserService
 * @since 0.1
 * @version 0.1
 */
@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter{

    private final JWTService jwtService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);

    /**
     * Processes each HTTP request to extract and validate the JWT token from the Authorization header.
     * If the token is valid, the user's authentication is set in the SecurityContextHolder,
     * indicating that the user is authenticated for the duration of the request.
     * <p>
     * Extracts the JWT token using the 'Bearer ' prefix and validates its integrity and expiration.
     * If validation passes, it retrieves the user details associated with the token's subject (username)
     * and sets up the security context with the user's authorities for authorization during the request.
     * </p>
     *
     * @param request The request object containing the headers.
     * @param response The response object.
     * @param filterChain The filter chain to which the request is passed for further processing.
     * @throws ServletException If an error occurs during the filtering process.
     * @throws IOException If an I/O error is encountered while processing the request or response.
     */
// TODO: Make this into three separate methods: doFilterInternal, extractJwtFromRequest, and authenticateUser
    @Override
    protected void doFilterInternal(@SuppressWarnings("null") @NonNull HttpServletRequest request, @SuppressWarnings("null") @NonNull HttpServletResponse response, @SuppressWarnings("null") @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String jwt, username;
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        logger.info("JWT recived through the http filter: " + jwt);

        username = jwtService.extractUsername(jwt);
        logger.info("Username found using token: " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("User found with token: " + username);
            User user = this.userService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
