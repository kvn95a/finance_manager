package com.kevin.finance.finance_manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    /** 
     * Constructor for SecurityConfig. This constructor is used to inject the JwtAuthFilter dependency into the SecurityConfig class. 
     * The JwtAuthFilter will be added to the security filter chain to handle JWT authentication for incoming requests.
     * @param jwtAuthFilter the JWT authentication filter to be used in the security configuration
     * @author Kevin
    */
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /** 
     * Configures the security filter chain for the application. This method defines the security settings for HTTP requests, 
     * including disabling CSRF protection, setting the session management policy to stateless, 
     * and specifying which endpoints are publicly accessible and which require authentication. 
     * It also adds the JwtAuthFilter to the filter chain to handle JWT authentication.
     * @param http the HttpSecurity object used to configure security settings
     * @return a SecurityFilterChain object that defines the security configuration for the application
     * @throws Exception if an error occurs while configuring the security filter chain
     * @author Kevin
    */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /** 
     * Defines a bean for the PasswordEncoder interface. This method returns an instance of BCryptPasswordEncoder, 
     * which is a password hashing algorithm that provides strong security for storing user passwords. 
     * The PasswordEncoder bean will be used by the authentication system to encode and verify user passwords.
     * @return a PasswordEncoder instance that uses BCrypt hashing
     * @author Kevin
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** 
     * Defines a bean for the AuthenticationManager interface. This method retrieves the AuthenticationManager from the AuthenticationConfiguration, 
     * which is responsible for processing authentication requests and managing user authentication. 
     * The AuthenticationManager bean will be used by the authentication system to authenticate users based on their credentials.
     * @param config the AuthenticationConfiguration object used to retrieve the AuthenticationManager
     * @return an AuthenticationManager instance that can be used for authenticating users
     * @throws Exception if an error occurs while retrieving the AuthenticationManager
     * @author Kevin
    */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
