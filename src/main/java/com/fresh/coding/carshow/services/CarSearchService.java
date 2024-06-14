package com.fresh.coding.carshow.services;

import com.fresh.coding.carshow.dtos.responses.CarWithImageSummarized;

import java.util.List;

public interface CarSearchService {
    List<CarWithImageSummarized> findCarWithImagesByModelAndBrand(String brand, String model);

    List<CarWithImageSummarized> findCarWithImagesByType(String type);

    List<CarWithImageSummarized> findCarWithImagesByTypeMotor(String typeMotor);

    List<CarWithImageSummarized> findCars(String brand, String model, String name, String type, Long priceMin, Long priceMax);

    List<CarWithImageSummarized> findCarWithImagesByIntervalPrice(Long minPrice, Long maxPrice);
}
