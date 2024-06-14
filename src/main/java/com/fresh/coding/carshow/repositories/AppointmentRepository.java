package com.fresh.coding.carshow.repositories;

import com.fresh.coding.carshow.entities.Appointment;
import com.fresh.coding.carshow.enums.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findByStatus(AppointmentStatus status, Pageable pageable);

    @Query("SELECT a FROM Appointment a WHERE a.status <> ?1")
    Page<Appointment> findAllByStatusNotEquals(AppointmentStatus status, Pageable pageable);
}
