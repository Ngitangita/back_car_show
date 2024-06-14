package com.fresh.coding.carshow.entities;

import com.fresh.coding.carshow.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "\"appointments\"")
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@Accessors(chain = true)
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String firstName;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String contact;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant appointmentDate;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Car car;
}
