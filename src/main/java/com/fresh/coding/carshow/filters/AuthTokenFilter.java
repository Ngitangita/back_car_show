package com.fresh.coding.carshow.filters;

import com.fresh.coding.carshow.repositories.UserRepository;
import com.fresh.coding.carshow.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final UserRepository userRepository;

    public AuthTokenFilter(JwtService jwtService, UserRepository userService) {
        this.jwtService = jwtService;
        this.userRepository = userService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        String jwt;
        String email;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        email = jwtService.extractUsername(jwt);
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid Email"));
            if (jwtService.isTokenValid(jwt, userDetails)) {
                var securityContext = SecurityContextHolder.createEmptyContext();
                var token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}
