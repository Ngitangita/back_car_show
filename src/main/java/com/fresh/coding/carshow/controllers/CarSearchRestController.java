package com.fresh.coding.carshow.controllers;

import com.fresh.coding.carshow.dtos.responses.CarWithImageSummarized;
import com.fresh.coding.carshow.services.CarSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarSearchRestController {
    private final CarSearchService carSearchService;

    @GetMapping("/search/model/brand")
    public List<CarWithImageSummarized> getCarWithImagesByModelAndBrand(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model
    ) {
        System.out.println(brand);
        return carSearchService.findCarWithImagesByModelAndBrand(
                brand,
                model
        );
    }

    @GetMapping("/search/type")
    public List<CarWithImageSummarized> getCarWithImagesByType(
            @RequestParam(required = false) String type
    ) {
        return carSearchService.findCarWithImagesByType(
                type
        );
    }


    @GetMapping("/search/type-motor")
    public List<CarWithImageSummarized> getCarWithImagesByTypeMotor(
            @RequestParam(required = false) String typeMotor
    ) {
        return carSearchService.findCarWithImagesByTypeMotor(
                typeMotor
        );
    }

    @GetMapping("/search/interval-price")
    public List<CarWithImageSummarized> getCarWithImagesByIntervalPrice(
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice
    ) {
        return carSearchService.findCarWithImagesByIntervalPrice(
                minPrice,
                maxPrice
        );
    }

    @GetMapping("/search")
    public List<CarWithImageSummarized> searchCars(
            @RequestParam String brand,
            @RequestParam String model,
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam Long priceMin,
            @RequestParam Long priceMax) {
        return carSearchService.findCars(brand, model, name, type, priceMin, priceMax);
    }
}
