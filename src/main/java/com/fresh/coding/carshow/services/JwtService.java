package com.fresh.coding.carshow.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateJwtToken(UserDetails userDetails);

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
