package com.example.demo.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY =
            "mySuperSecureJwtSecretKeyForJobPortalApplication2026";

    public String generateToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60)
                )
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }
    public String extractEmail(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes())
                )
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String extractRole(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes())
                )
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }

    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(
                            Keys.hmacShaKeyFor(SECRET_KEY.getBytes())
                    )
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

}