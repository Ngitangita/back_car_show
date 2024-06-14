package com.fresh.coding.carshow.dtos.responses;

import com.fresh.coding.carshow.enums.AppointmentStatus;

public record AppointmentSummarized(
        Long id,
        String name,
        String firstName,
        String email,
        String message,
        String contact,
        String appointmentDate,
        AppointmentStatus status,
        CarSummarized car
) {
}
