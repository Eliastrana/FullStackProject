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
 * The {@code JWTAuthFilter} class extends {@link OncePerRequestFilter} to intercept
 * HTTP requests and perform JWT-based authentication. It extracts a JWT from the
 * {@code Authorization} header of incoming requests and validates the token. If valid,
 * the filter authenticates the user associated with the token, setting the authentication
 * in the {@link SecurityContextHolder} to enable secure access to protected resources.
 *
 * @author vegard johnsen, sander rom skofsrud
 * @version 0.1
 * @since 0.1
 * @see JWTService for handling JWT extraction and validation.
 * @see UserService for loading user details.
 * @see User for the user entity associated with the JWT.
 */
@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);

    /**
     * Processes each incoming request, extracts the JWT, validates it, and sets up
     * authentication if the token is valid.
     *
     * @param request The incoming HTTP request.
     * @param response The outgoing HTTP response.
     * @param filterChain The filter chain to continue the request-response flow.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String jwt = extractJwtFromRequest(request);
        if (jwt != null) {
            authenticateUser(jwt, request);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT from the {@code Authorization} header by stripping the "Bearer " prefix.
     *
     * @param request The request from which to extract the JWT.
     * @return The extracted JWT as a {@link String}, or {@code null} if the header is not present or doesn't contain a bearer token.
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            logger.info("JWT received through the HTTP filter: " + jwt);
            return jwt;
        }
        return null;
    }

    /**
     * Authenticates the user associated with the given JWT if the token is valid.
     * Sets the {@link UsernamePasswordAuthenticationToken} in the {@link SecurityContextHolder}.
     *
     * @param jwt The JWT to validate and use for authentication.
     * @param request The current HTTP request, used to build authentication details.
     */
    private void authenticateUser(String jwt, HttpServletRequest request) {
        final String username = jwtService.extractUsername(jwt);
        logger.info("Username found using token: " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = this.userService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("User authenticated with token: " + username);
            }
        }
    }
}
