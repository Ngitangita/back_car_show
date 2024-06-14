package com.fresh.coding.carshow.services.impl;

import com.fresh.coding.carshow.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Override
    public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject((userDetails.getUsername()))
                .issuedAt(new Date())
                .claims(Map.of("scope", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "))))
                .expiration(new Date((new Date()).getTime() + getExpirationTime()))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final var username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey() {
        var keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
