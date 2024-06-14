package com.fresh.coding.carshow.controllers;

import com.fresh.coding.carshow.dtos.requests.LoginRequest;
import com.fresh.coding.carshow.dtos.requests.UserRequest;
import com.fresh.coding.carshow.dtos.responses.AuthResponse;
import com.fresh.coding.carshow.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthRestController {
    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping
    public AuthResponse register(@RequestBody @Valid UserRequest userRequest) {
        return authService.register(userRequest);
    }
}
