package com.fresh.coding.carshow.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Entity
@Table(name = "\"images\"")
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@Accessors(chain = true)
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String url;

    @ManyToOne
    private Car car;
}
