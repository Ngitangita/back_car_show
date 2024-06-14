package com.fresh.coding.carshow.controllers;

import com.fresh.coding.carshow.dtos.requests.AppointmentRequest;
import com.fresh.coding.carshow.dtos.requests.UpdateStatus;
import com.fresh.coding.carshow.dtos.responses.AppointmentSummarized;
import com.fresh.coding.carshow.dtos.responses.Paginate;
import com.fresh.coding.carshow.enums.AppointmentStatus;
import com.fresh.coding.carshow.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentRestController {
    private final AppointmentService appointmentService;

    @PostMapping
    public AppointmentSummarized createAppointment(@RequestBody @Valid AppointmentRequest appointmentRequest) {
        return appointmentService.createAppointment(appointmentRequest);
    }

    @GetMapping
    public Paginate<List<AppointmentSummarized>> getAllAppointments(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer perPage
    ) {
        return appointmentService.findAllAppointments(page, perPage);
    }

    @GetMapping("/{id}")
    public AppointmentSummarized getAppointment(@PathVariable Long id) {
        return appointmentService.findAppointment(id);
    }


    @PutMapping("/{id}/{status}")
    public AppointmentSummarized updateStatusAppointment(@PathVariable Long id, @PathVariable UpdateStatus status) {
        return appointmentService.updateStatusAppointment(id, status.appointmentStatus().name());
    }

    @GetMapping("/status/{status}")
    public Paginate<List<AppointmentSummarized>> getAllAppointmentByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer perPage
    ) {
        return appointmentService.findAllAppointmentByStatus(AppointmentStatus.valueOf(status), page, perPage);
    }


    @GetMapping("/status-not-equals/{status}")
    public Paginate<List<AppointmentSummarized>> getAllByStatusNotEquals(
            @PathVariable String status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer perPage
    ) {
        return appointmentService.findAllByStatusNotEquals(AppointmentStatus.valueOf(status), page, perPage);
    }
}
