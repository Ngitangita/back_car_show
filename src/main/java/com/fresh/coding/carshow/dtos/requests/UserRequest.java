package com.fresh.coding.carshow.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserRequest(
        Long id,

        @Email(message = "Please provide a valid email address.")
        String email,

        @NotEmpty(message = "Name cannot be empty.")
        @NotBlank(message = "Name cannot be blank.")
        @Size(min = 3, message = "Name must be at least 3 characters long.")
        String name,

        @NotEmpty(message = "Password cannot be empty.")
        @NotBlank(message = "Password cannot be blank.")
        @Size(min = 5, message = "Password must be at least 5 characters long.")
        String password
) {
}
