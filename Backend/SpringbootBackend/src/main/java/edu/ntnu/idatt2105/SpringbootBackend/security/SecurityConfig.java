package edu.ntnu.idatt2105.SpringbootBackend.security;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures the security for the web application.
 * This class is responsible for setting up security
 * concerns such as CORS, CSRF protection, and the stateless session policy.
 * It specifies both public
 * and protected endpoints and incorporates the JWT authentication filter into the Spring Security filter chain.
 * <p>
 * This configuration replaces the deprecated method of extending WebSecurityConfigurerAdapter, directly
 * defining a SecurityFilterChain bean for greater flexibility and control over security configurations.
 * </p>
 *
 * @author Vegard Johnsen, Sander R. Skofsrud
 * @see JWTAuthFilter
 * @see SecurityFilterChain
 * @since 0.1
 * @version 0.1
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    /**
     * Configures HTTP security settings for the application, including setting the session management
     * policy to stateless and disabling CSRF to support a stateless API architecture.
     * It also applies
     * CORS configurations and defines secure access to API endpoints, integrating the {@link JWTAuthFilter}
     * for JWT-based authentication and authorization.
     *
     * @param http The {@link HttpSecurity} to configure.
     * @return The configured {@link SecurityFilterChain}.
     * @throws Exception If an error occurs during the configuration process.
     */
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/swagger-ui/index.html", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**", "/error").permitAll()
                        .requestMatchers("/home", "/login", "api/user/register", "api/user/login").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    /**
     * Configures CORS settings for the application, allowing requests from any origin
     * to access the API endpoints with the specified HTTP methods.
     *
     * @return The configured {@link WebMvcConfigurer}.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@SuppressWarnings("null") @NotNull CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }

}
