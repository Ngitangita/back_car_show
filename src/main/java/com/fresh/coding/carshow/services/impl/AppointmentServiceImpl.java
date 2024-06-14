package com.fresh.coding.carshow.services.impl;

import com.fresh.coding.carshow.dtos.requests.AppointmentRequest;
import com.fresh.coding.carshow.dtos.responses.AppointmentSummarized;
import com.fresh.coding.carshow.dtos.responses.Paginate;
import com.fresh.coding.carshow.entities.Appointment;
import com.fresh.coding.carshow.enums.AppointmentStatus;
import com.fresh.coding.carshow.exceptions.NotFoundException;
import com.fresh.coding.carshow.mappers.AppointmentMapper;
import com.fresh.coding.carshow.repositories.AppointmentRepository;
import com.fresh.coding.carshow.repositories.CarRepository;
import com.fresh.coding.carshow.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final CarRepository carRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public AppointmentSummarized createAppointment(AppointmentRequest appointmentRequest) {
        var car = carRepository.findById(appointmentRequest.carId()).orElseThrow(() -> new NotFoundException("Car not found"));
        var appointment = appointmentMapper.toEntity(appointmentRequest);
        appointment.setCar(car);
        var savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponse(savedAppointment);
    }


    @Override
    public AppointmentSummarized findAppointment(Long id) {
        var foundAppointment = appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
        return appointmentMapper.toResponse(foundAppointment);
    }

    @Override
    public AppointmentSummarized updateStatusAppointment(Long id, String status) {
        var foundAppointment = appointmentRepository.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
        var newStatus = AppointmentStatus.valueOf(status);
        foundAppointment.setStatus(newStatus);
        var savedAppointment = appointmentRepository.save(foundAppointment);
        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    public Paginate<List<AppointmentSummarized>> findAllAppointmentByStatus(AppointmentStatus status, Integer page, Integer perPage) {
        var pageRequest = PageRequest.of(page - 1, perPage);
        return getListPaginate(appointmentRepository.findByStatus(status, pageRequest));
    }

    @Override
    public Paginate<List<AppointmentSummarized>> findAllByStatusNotEquals(AppointmentStatus status, Integer page, Integer perPage) {
        var pageRequest = PageRequest.of(page - 1, perPage);
        return this.getListPaginate(appointmentRepository.findAllByStatusNotEquals(status, pageRequest));
    }

    @Override
    public Paginate<List<AppointmentSummarized>> findAllAppointments(Integer page, Integer perPage) {
        var pageRequest = PageRequest.of(page - 1, perPage);
        return this.getListPaginate(appointmentRepository.findAll(pageRequest));
    }

    private Paginate<List<AppointmentSummarized>> getListPaginate(Page<Appointment> appointments) {
        var items = appointments.getContent().stream().map(appointmentMapper::toResponse).toList();
        return new Paginate<>(
                items,
                appointments.getNumber(),
                appointments.getTotalPages(),
                appointments.getTotalElements()
        );
    }
}
