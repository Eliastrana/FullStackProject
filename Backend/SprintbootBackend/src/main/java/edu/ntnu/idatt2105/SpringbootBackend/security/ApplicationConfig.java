package edu.ntnu.idatt2105.SpringbootBackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.ntnu.idatt2105.SpringbootBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationProvider;



/**
 * Configuration class for the application.
 * Contains beans for password encoding, user details service and authentication provider.

 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    final private UserRepository userRepository;

    /**
     * Creates a new UserDetailsService bean.
     *
     * @return An instance of the UserDetailsService.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    /**
     * Creates a new AuthenticationProvider bean.
     *
     * @return An instance of the AuthenticationProvider.
     */

    @Bean
    public AuthenticationProvider AuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Creates a new AuthenticationManager bean.
     *
     * @param config The AuthenticationConfiguration.
     * @return An instance of the AuthenticationManager.
     * @throws Exception If an error occurs.
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates a new PasswordEncoder bean.
     *
     * @return An instance of the PasswordEncoder.
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
