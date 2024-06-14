package com.fresh.coding.carshow.dtos.requests;

import com.fresh.coding.carshow.enums.CarStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CarRequest(
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotBlank
        String brand,

        @NotBlank
        String model,

        @NotNull
        Long price,

        @NotBlank
        String color,

        @NotBlank
        String motorType,

        @NotBlank
        String type,

        @NotNull
        @Min(0)
        Integer power,

        @NotNull
        @Size(min = 1)
        String placeNumber,

        @NotNull
        CarStatus status
) {
}
