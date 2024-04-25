package com.example.todo.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;


@Component
public class JwtTokenProvider {

    @Value("${app.jwt.expiration}")
    private int jwtExpirationInMs;

    private final SecretKey jwtSecretKey;

    public JwtTokenProvider(@Value("${app.jwt.secret}") String jwtSecret) {
        // Generate a secure key for HS512
        this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String createToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | IllegalArgumentException ex) {
            // Log the exception or handle it as needed
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(jwtSecretKey)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
    
        String username = claims.getSubject();
    
        // No roles present, return an empty list of authorities
        return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
    }
    
}