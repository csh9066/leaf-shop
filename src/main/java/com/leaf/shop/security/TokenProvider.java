package com.leaf.shop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class TokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;
    // 30일
    public final static long ACCESS_TOKEN_EXPIRATION_SECOND = 60 * 60 * 24 * 300L;
    public final static String ACCESS_TOKEN_COOKIE_NAME = "access_token";


    public String generateTokenByUserId(long userId) {
        Date expirationDate = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_SECOND) ;
        Claims claims = Jwts.claims();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getKey())
                .compact();
    }

    public long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean isValidateToken(String token) {
        try {
            getUserIdFromToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
