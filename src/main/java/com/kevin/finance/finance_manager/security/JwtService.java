package com.kevin.finance.finance_manager.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final String issuer;
    private final long expirationMillis;

    /**
     * Constructor for JwtService.
     * Ensures the secret key is of sufficient length for security (at least 32 characters for HMAC SHA-256).
     * @param secret The secret key used for signing JWTs, injected from application properties.
     * @param issuer The issuer of the JWTs, injected from application properties.
     * @param expMinutes The expiration time in minutes for the JWTs, injected from application properties.
     */
    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.issuer}") String issuer,
            @Value("${app.jwt.exp-minutes}") long expMinutes
    ) {

        // Ensure the secret key is at least 32 characters (256 bits) for security
        if (secret.length() < 32) {
            throw new IllegalStateException("JWT secret must be at least 32 characters long (256 bits).");
        }

        //convert plain text secret string into a proper cryptographic key object that the JJWT library can use to sign tokens.
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.issuer = issuer;
        this.expirationMillis = expMinutes * 60 * 1000; // Convert minutes to milliseconds
    }

    /**
     * Generates a JWT for the given user details.
     * The token includes the username as the subject, the issuer, and an expiration time.
     * @param userDetails The user details for which the token is being generated.
     * @return A signed JWT as a String.
     */
    public String generateToken(UserDetails userDetails) {

        Instant now = Instant.now(); // Current time for issuedAt and expiration calculations

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMillis)))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Extracts the username (subject) from the given JWT token.
     * This method will throw an exception if the token is invalid or if the issuer does not match.
     * @param token The JWT token from which to extract the username.
     * @return The username (subject) extracted from the token.
     */
    public String extractUsername(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * Validates the given JWT token against the provided user details.
     * This method checks if the token is valid, not expired, and if the subject matches the username in the user details.
     * @param token The JWT token to validate.
     * @param userDetails The user details against which to validate the token.
     * @return true if the token is valid and matches the user details, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            Claims claims = parseToken(token);
            return claims.getSubject().equals(userDetails.getUsername());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Centralized parsing with issuer enforcement
    /**
     * Parses the given JWT token and returns the claims.
     * This method enforces that the token has the correct issuer and is signed with the expected secret key.
     * If the token is invalid, expired, or has an incorrect issuer, an exception will be thrown.
     * @param token The JWT token to parse.
     * @return The claims contained in the token if it is valid.
     */
    private Claims parseToken(String token) {

        return Jwts.parser()
                .requireIssuer(issuer)
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
