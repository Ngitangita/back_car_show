package com.fresh.coding.carshow.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fresh.coding.carshow.enums.CarStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CarSummarized(
        Long id,
        String name,
        String description,
        String brand,
        String model,
        Long price,
        String color,
        String motorType,
        String type,
        Integer power,
        String placeNumber,
        CarStatus status,
        List<ImageSummarized> imageUrls
) {
    public CarSummarized {
        if (imageUrls != null && imageUrls.isEmpty()) {
            imageUrls = null;
        }
    }
}
