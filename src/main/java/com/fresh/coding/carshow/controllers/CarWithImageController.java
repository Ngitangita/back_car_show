package com.fresh.coding.carshow.controllers;

import com.fresh.coding.carshow.dtos.responses.CarWithImageSummarized;
import com.fresh.coding.carshow.services.CarWithImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarWithImageController {
    private final CarWithImageService carWithImageService;

    @GetMapping("/{carId}")
    public CarWithImageSummarized getCarWithImage(@PathVariable Long carId) {
        return carWithImageService.findCarWithImage(carId);
    }
}
