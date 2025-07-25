package com.prep_saga.PrepSaga.security;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    private SecretKey jwtSecretKey;

    @PostConstruct
    public void init() {
        this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    private final long jwtExpirationInMs = 86400000; // 24 hours

    /**
     * Creates a JWT token for a user. Can be used for both normal and OAuth2 users.
     *
     * @param email the email of the user
     * @param role the role of the user
     * @return the JWT token
     */
    public String createToken(String email, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(email)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Create a JWT token from OAuth2User details (Google, Facebook, etc.).
     * This method is used when the user logs in via OAuth2.
     *
     * @param oauth2User the OAuth2User object that contains the user details
     * @return the JWT token
     */
    public String createTokenFromOAuth2User(OAuth2User oauth2User) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        // Extract user details from OAuth2User
        String email = oauth2User.getAttribute("email");  // OAuth2 attribute
        String role = "USER"; // Default to "USER", you can adjust it based on your OAuth2 data

        // Optionally, you can extract roles or other attributes from the OAuth2User object
        Map<String, Object> attributes = oauth2User.getAttributes();
        if (attributes.containsKey("role")) {
            role = (String) attributes.get("role");
        }

        return Jwts.builder()
                .setSubject(email)
                .claim("email", email)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Extract username (email) from JWT token.
     *
     * @param token the JWT token
     * @return the username (email)
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Validate the JWT token and check if it is expired or invalid.
     *
     * @param authToken the JWT token
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(authToken); // Will throw exception if invalid
            return true;
        } catch (SecurityException | MalformedJwtException ex) {
            System.out.println("Invalid JWT signature");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    /**
     * Get the role from the JWT token.
     *
     * @param token the JWT token
     * @return the role of the user
     */
    public String getRoleFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class); // Retrieve role from token
    }

    /**
     * Get the email from the JWT token.
     *
     * @param token the JWT token
     * @return the email of the user
     */
    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("email", String.class); // Retrieve email from token
    }
}
