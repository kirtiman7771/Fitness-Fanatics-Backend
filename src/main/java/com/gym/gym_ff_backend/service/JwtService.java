package com.gym.gym_ff_backend.service;

import com.gym.gym_ff_backend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "fitness-fanatics-gym-management-system-secret-key";

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60; // 1 hour

    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes(StandardCharsets.UTF_8)
            );

    public String generateToken(User user) {

        Date now = new Date();

        Date expiration =
                new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String email = extractEmail(token);

        return email != null
                && email.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        Date expiration =
                extractClaim(
                        token,
                        Claims::getExpiration
                );

        return expiration.before(new Date());
    }

    private <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims =
                Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

        return claimsResolver.apply(claims);
    }
}