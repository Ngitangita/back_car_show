package com.fresh.coding.carshow.services.impl;

import com.fresh.coding.carshow.dtos.responses.CarWithImageSummarized;
import com.fresh.coding.carshow.entities.Car;
import com.fresh.coding.carshow.mappers.CarMapper;
import com.fresh.coding.carshow.repositories.CarRepository;
import com.fresh.coding.carshow.repositories.ImageRepository;
import com.fresh.coding.carshow.services.CarSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarSearchServiceImpl implements CarSearchService {
    private final CarRepository carRepository;
    private final ImageRepository imageRepository;
    private final CarMapper carMapper;

    @Override
    public List<CarWithImageSummarized> findCarWithImagesByModelAndBrand(String brand, String model) {
        List<Car> res;
        if ((brand == null || brand.isEmpty()) && model != null) {
            res = carRepository.findAllByModelContainingIgnoreCase(model);
        } else if ((model == null || model.isEmpty()) && brand != null) {
            res = carRepository.findAllByBrandContainingIgnoreCase(brand);
        } else if (brand == null) {
            res = carRepository.findAll();
        } else {
            res = carRepository.findAllByBrandContainingIgnoreCaseAndModelContainingIgnoreCase(brand, model);
        }
        return mapCarsToResponse(res);
    }


    @Override
    public List<CarWithImageSummarized> findCarWithImagesByType(String type) {
        List<Car> cars;
        if (type != null) {
            cars = carRepository.findAllByTypeContainingIgnoreCase(type);
        } else {
            cars = carRepository.findAll();
        }
        return mapCarsToResponse(cars);
    }

    @Override
    public List<CarWithImageSummarized> findCarWithImagesByTypeMotor(String typeMotor) {
        List<Car> cars;
        if (typeMotor != null) {
            cars = carRepository.findAllByMotorTypeContainingIgnoreCase(typeMotor);
        } else {
            cars = carRepository.findAll();
        }
        return mapCarsToResponse(cars);
    }

    @Override
    public List<CarWithImageSummarized> findCars(String brand, String model, String name, String type, Long priceMin, Long priceMax) {
        var res = carRepository.findCarsWithCriteria(brand, model, name, type, priceMin, priceMax);
        return mapCarsToResponse(res);
    }

    @Override
    public List<CarWithImageSummarized> findCarWithImagesByIntervalPrice(Long minPrice, Long maxPrice) {
        List<Car> cars;
        if (minPrice != null && maxPrice != null) {
            cars = carRepository.findAllByPriceBetween(minPrice, maxPrice);
        } else if (minPrice != null) {
            cars = carRepository.findAllByPriceGreaterThanEqual(minPrice);
        } else if (maxPrice != null) {
            cars = carRepository.findAllByPriceLessThanEqual(maxPrice);
        } else {
            cars = carRepository.findAll();
        }
        return mapCarsToResponse(cars);
    }


    private List<CarWithImageSummarized> mapCarsToResponse(List<Car> cars) {
        return cars.stream().map(car -> {
            var images = imageRepository.findAllByCarId(car.getId());
            return carMapper.toResponse(car, images);
        }).collect(Collectors.toList());
    }

}
