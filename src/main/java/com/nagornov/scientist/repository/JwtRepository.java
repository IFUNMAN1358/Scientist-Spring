package com.nagornov.scientist.repository;

import com.nagornov.scientist.config.properties.JwtProperties;
import com.nagornov.scientist.model.Role;
import com.nagornov.scientist.model.User;
import com.nagornov.scientist.util.CustomLogger;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtRepository {

    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;
    private final Integer accessExpireMinutes;
    private final Integer refreshExpireDays;

    public JwtRepository(JwtProperties jwtProperties) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getAccessSecret()));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getRefreshSecret()));
        this.accessExpireMinutes = jwtProperties.getAccessExpireMinutes();
        this.refreshExpireDays = jwtProperties.getRefreshExpireDays();
    }

    @SneakyThrows
    public String generateAccessToken(@NonNull User user) {
        List<String> roles = user.getRoles().stream()
            .map(Role::getAuthority)
            .collect(Collectors.toList());

        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(accessExpireMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserId()))
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("roles", roles)
                .compact();
    }

    public String generateRefreshToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(refreshExpireDays).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserId()))
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            CustomLogger.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            CustomLogger.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            CustomLogger.error("Malformed jwt");
        } catch (SignatureException sEx) {
            CustomLogger.error("Invalid signature");
        } catch (Exception e) {
            CustomLogger.error("invalid token");
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
