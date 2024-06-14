package com.fresh.coding.carshow.dtos.responses;

import com.fresh.coding.carshow.enums.CarStatus;

import java.util.List;

public record CarWithImageSummarized(
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
        List<ImageSummarized> images
) {
}
