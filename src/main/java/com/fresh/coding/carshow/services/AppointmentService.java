package com.fresh.coding.carshow.services;

import com.fresh.coding.carshow.dtos.requests.AppointmentRequest;
import com.fresh.coding.carshow.dtos.responses.AppointmentSummarized;
import com.fresh.coding.carshow.dtos.responses.Paginate;
import com.fresh.coding.carshow.enums.AppointmentStatus;

import java.util.List;

public interface AppointmentService {
    AppointmentSummarized createAppointment(AppointmentRequest appointmentRequest);

    Paginate<List<AppointmentSummarized>> findAllAppointments(Integer page, Integer perPage);

    AppointmentSummarized findAppointment(Long id);

    AppointmentSummarized updateStatusAppointment(Long id, String status);

    Paginate<List<AppointmentSummarized>> findAllAppointmentByStatus(AppointmentStatus status, Integer page, Integer perPage);

    Paginate<List<AppointmentSummarized>> findAllByStatusNotEquals(AppointmentStatus status, Integer page, Integer perPage);
}
