package com.onlikee.auth.service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService {

    private final SecretKey secretKey;
    private final long expireSeconds;

    public JwtTokenService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expire-seconds}") long expireSeconds) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("security.jwt.secret is required");
        }
        if (secret.length() < 32) {
            throw new IllegalStateException("security.jwt.secret must be at least 32 characters");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expireSeconds = expireSeconds;
    }

    public String generateToken(String uuid) {
        if (uuid == null || uuid.isBlank()) {
            throw new IllegalArgumentException("uuid is required");
        }
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(expireSeconds);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(uuid)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();
    }

    public JwtTokenPayload parseToken(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("token is required");
        }
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Instant issuedAt = claims.getIssuedAt() == null ? null : claims.getIssuedAt().toInstant();
        Instant expiresAt = claims.getExpiration() == null ? null : claims.getExpiration().toInstant();
        return new JwtTokenPayload(claims.getId(), claims.getSubject(), issuedAt, expiresAt);
    }

    public static final class JwtTokenPayload {

        private final String tokenId;
        private final String uuid;
        private final Instant issuedAt;
        private final Instant expiresAt;

        public JwtTokenPayload(String tokenId, String uuid, Instant issuedAt, Instant expiresAt) {
            this.tokenId = tokenId;
            this.uuid = uuid;
            this.issuedAt = issuedAt;
            this.expiresAt = expiresAt;
        }

        public String getTokenId() {
            return tokenId;
        }

        public String getUuid() {
            return uuid;
        }

        public Instant getIssuedAt() {
            return issuedAt;
        }

        public Instant getExpiresAt() {
            return expiresAt;
        }
    }
}
