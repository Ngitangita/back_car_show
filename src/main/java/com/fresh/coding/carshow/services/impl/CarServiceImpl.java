package com.fresh.coding.carshow.services.impl;

import com.fresh.coding.carshow.dtos.requests.CarRequest;
import com.fresh.coding.carshow.dtos.responses.CarSummarized;
import com.fresh.coding.carshow.dtos.responses.CarWithImageSummarized;
import com.fresh.coding.carshow.dtos.responses.Paginate;
import com.fresh.coding.carshow.entities.Car;
import com.fresh.coding.carshow.entities.Image;
import com.fresh.coding.carshow.enums.CarStatus;
import com.fresh.coding.carshow.exceptions.NotFoundException;
import com.fresh.coding.carshow.files.FileService;
import com.fresh.coding.carshow.mappers.CarMapper;
import com.fresh.coding.carshow.mappers.ImageMapper;
import com.fresh.coding.carshow.repositories.CarRepository;
import com.fresh.coding.carshow.repositories.ImageRepository;
import com.fresh.coding.carshow.services.CarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ImageRepository imageRepository;
    private final FileService fileService;
    private final CarMapper carMapper;
    private final ImageMapper imageMapper;


    @Transactional
    @Override
    public List<CarSummarized> createAllCars(List<CarRequest> carRequests) {
        var carsSaved = new ArrayList<CarSummarized>();
        for (var carRequest : carRequests) {
            var car = carMapper.toEntity(carRequest);
            var saved = carRepository.save(car);
            var carRes = carMapper.toResponse(saved);
            carsSaved.add(carRes);
        }
        return carsSaved;
    }

    @Override
    public List<CarWithImageSummarized> findAllCars() {
        var cars = carRepository.findAll();
        var carsWithImagesSummarized = new ArrayList<CarWithImageSummarized>();

        for (var car : cars) {
            var images = imageRepository.findAllByCarId(car.getId());
            var carWithImage = carMapper.toResponse(car, images);
            carsWithImagesSummarized.add(carWithImage);
        }
        return carsWithImagesSummarized;
    }

    @Override
    public List<String> findAllBrandOfCars() {
        return carRepository.findAllBrandOfCars().stream()
                .map(String::toLowerCase)
                .distinct()
                .collect(Collectors.toList());
    }


    @Override
    public List<CarSummarized> findAllCarByStatusPinned(Integer limit) {
        var page = PageRequest.of(0, limit);
        var cars = carRepository.findByStatus(CarStatus.PINNED, page).getContent();
        return cars.stream()
                .map(carMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllMotorTypeOfCars() {
        return carRepository.findAllMotorTypeOfCars()
                .stream()
                .map(String::toLowerCase)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllTypeOfCars() {
        return carRepository.findAllTypeOfCars()
                .stream()
                .map(String::toLowerCase)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllColorsOfCars() {
        return carRepository.findAllColors().stream()
                .map(String::toLowerCase)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public CarSummarized modifyCarById(Long id, CarRequest carRequest) {
        var car = carRepository.findById(id).orElseThrow(() -> new NotFoundException("Car not found"));
        var newCar = carMapper.toEntity(carRequest);
        newCar.setId(car.getId());
        var savedCar = carRepository.save(newCar);
        return carMapper.toResponse(savedCar);
    }

    @org.springframework.transaction.annotation.Transactional
    @Override
    public CarSummarized deleteCarById(Long id) {
        var car = carRepository.findById(id).orElseThrow(() -> new NotFoundException("Car not found"));
        var images = imageRepository.findByCar_Id(car.getId());
        for (var image : images) {
            fileService.deleteFile(image.getUrl());
        }
        imageRepository.deleteAll(images);
        carRepository.deleteById(car.getId());
        return carMapper.toResponse(car);
    }

    @Override
    public Paginate<List<CarWithImageSummarized>> paginateCars(Integer page, Integer perPage) {
        var pageRequest = PageRequest.of(page - 1, perPage);
        var carsPage = carRepository.findAll(pageRequest);
        return getListPaginate(carsPage);
    }

    @Override
    public Paginate<List<CarWithImageSummarized>> findCarsByTypeAndExcludeId(String type, Long id, Integer page, Integer perPage) {
        var pageRequest = PageRequest.of(page - 1, perPage);
        var carsPage = carRepository.findAllByTypeAndExcludeId(type, id, pageRequest);
        return getListPaginate(carsPage);
    }

    @Override
    public CarSummarized modifyStatusCarById(Long id, String status) {
        var car = carRepository.findById(id).orElseThrow(() -> new NotFoundException("Car not found"));
        car.setStatus(CarStatus.valueOf(status));
        var savedCar = carRepository.save(car);
        return carMapper.toResponse(savedCar);
    }

    @org.springframework.transaction.annotation.Transactional
    @Override
    public CarWithImageSummarized createCarWithImage(CarRequest carRequest, MultipartFile[] files) {
        var car = carMapper.toEntity(carRequest);
        var saved = carRepository.save(car);
        var toCreates = new ArrayList<Image>();
        for (var file : files) {
            var url = fileService.saveFile(file);
            var image = Image.builder().url(url).car(car).build();
            toCreates.add(image);
        }
        var savedImages = imageRepository.saveAll(toCreates);
        var images = savedImages.stream().map(imageMapper::toResponse).collect(Collectors.toList());
        return carMapper.toResponse(saved, images);
    }

    @Override
    public List<String> findAllPowerOfCars() {
        return carRepository.findAllPowerOfCars()
                .stream()
                .map(String::toLowerCase)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllModelOfCars() {
        return carRepository.findAllModelOfCars()
                .stream()
                .map(String::toLowerCase)
                .distinct()
                .collect(Collectors.toList());
    }

    private Paginate<List<CarWithImageSummarized>> getListPaginate(Page<Car> carsPage) {
        var items = carsPage.getContent().stream().map(car -> {
            var images = imageRepository.findByCar_Id(car.getId())
                    .stream().map(imageMapper::toResponse)
                    .collect(Collectors.toList());
            return carMapper.toResponse(car, images);
        }).collect(Collectors.toList());
        return new Paginate<>(
                items,
                carsPage.getNumber(),
                carsPage.getTotalPages(),
                carsPage.getTotalElements()
        );
    }


}
