package edu.ntnu.idatt2105.SpringbootBackend.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.security.Key;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

/**
 * Service class for managing JSON Web Token (JWT) operations.
 * It supports generating JWTs for user authentication, extracting claims like username and expiration date from a JWT,
 * and validating the token's integrity and expiration status.
 * This service is fundamental for the security layer of the application,
 * ensuring that only valid and non-expired tokens are accepted for authentication.
 *
 * @author Vegard Johnsen
 * @see UserDetails
 * @since 0.1
 * @version 0.1
 */
@Service
public class JWTService {
    private final Logger logger = LoggerFactory.getLogger(JWTService.class);
    private final String secretKey;


    public JWTService(@Value("${SECRET_KEY}") String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Extracts the username from the specified JWT token.
     *
     * @param token The JWT token from which the username is extracted.
     * @return The username as a {@code String}.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token based on the claims resolver function provided.
     *
     * @param token The JWT token from which claims are to be extracted.
     * @param claimsResolver A {@code Function} defining how to extract the claim from the {@code Claims}.
     * @param <T> The type of the claim being extracted.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Validates if a JWT token is valid for a specific user based on the user details provided.
     *
     * @param token The JWT token to validate.
     * @param userDetails The {@link UserDetails} against which the token is validated.
     * @return {@code true} if the token is valid, {@code false} otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if a JWT token has expired.
     *
     * @param token The JWT token to check for expiration.
     * @return {@code true} if the token has expired, {@code false} otherwise.
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token from which the expiration date is extracted.
     * @return The expiration date as a {@link Date}.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generates a JWT token for the user details provided.
     *
     * @param userDetails The {@link UserDetails} for which the token is generated.
     * @return A JWT token as a {@code String}.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claim = new HashMap<>();
        return generateToken(claim, userDetails);
    }

    /**
     * Generates a JWT token with specific claims for the user details provided.
     *
     * @param claims A {@code Map} of claims to be included in the token.
     * @param userDetails The {@link UserDetails} for which the token is generated.
     * @return A JWT token as a {@code String}.
     */
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts all claims from the specified JWT token.
     *
     * @param token The JWT token from which claims are to be extracted.
     * @return A {@link Claims} object containing all claims from the token.
     */
    private Claims extractAllClaims(String token) {
        logger.info("Token being checked: " + token);
        logger.info("Token length: " + token.length());
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Error parsing JWT token: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Retrieves the signing key used for generating and validating JWT tokens.
     *
     * @return The signing key as a {@link Key}.
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}