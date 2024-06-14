package com.fresh.coding.carshow.dtos.requests;

import com.fresh.coding.carshow.enums.AppointmentStatus;

public record UpdateStatus(
        AppointmentStatus appointmentStatus
) {
}
