package com.fresh.coding.carshow.services;

import com.fresh.coding.carshow.dtos.requests.LoginRequest;
import com.fresh.coding.carshow.dtos.requests.UserRequest;
import com.fresh.coding.carshow.dtos.responses.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);

    AuthResponse register(UserRequest userRequest);
}
