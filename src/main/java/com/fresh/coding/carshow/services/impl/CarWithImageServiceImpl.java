package com.fresh.coding.carshow.services.impl;

import com.fresh.coding.carshow.dtos.responses.CarWithImageSummarized;
import com.fresh.coding.carshow.exceptions.NotFoundException;
import com.fresh.coding.carshow.mappers.CarMapper;
import com.fresh.coding.carshow.repositories.CarRepository;
import com.fresh.coding.carshow.repositories.ImageRepository;
import com.fresh.coding.carshow.services.CarWithImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarWithImageServiceImpl implements CarWithImageService {
    private final CarRepository carRepository;
    private final ImageRepository imageRepository;
    private final CarMapper carMapper;

    @Override
    public CarWithImageSummarized findCarWithImage(Long id) {
        var car = carRepository.findById(id).orElseThrow(() -> new NotFoundException("Car not found !"));
        var images = imageRepository.findAllByCarId(car.getId());
        return carMapper.toResponse(car, images);
    }
}
