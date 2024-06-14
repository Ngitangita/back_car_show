package com.fresh.coding.carshow.dtos.requests;

import com.fresh.coding.carshow.enums.AppointmentStatus;
import jakarta.validation.constraints.*;

import java.time.Instant;

public record AppointmentRequest(
        Long id,
        @NotEmpty(message = "Name cannot be empty.")
        @NotBlank(message = "Name cannot be blank.")
        @NotNull
        String name,

        @NotEmpty(message = "Name cannot be empty.")
        @NotBlank(message = "Name cannot be blank.")
        @Size(min = 3, message = "FirstName must be at least 3 characters long.")
        @NotNull
        String firstName,

        @Email(message = "Please provide a valid email address.")
        @NotNull
        String email,

        @Size(min = 3, message = "Message must be at least 3 characters long.")
        @NotNull
        String message,

        @NotEmpty(message = "Contact cannot be empty.")
        @NotBlank(message = "Contact cannot be blank.")
        @Size(min = 3, message = "Contact must be at least 3 characters long.")
        @NotNull
        String contact,

        Instant appointmentDate,

        AppointmentStatus status,

        @NotNull
        Long carId
) {
}
