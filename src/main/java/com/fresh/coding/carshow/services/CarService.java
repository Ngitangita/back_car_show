package com.fresh.coding.carshow.services;

import com.fresh.coding.carshow.dtos.requests.CarRequest;
import com.fresh.coding.carshow.dtos.responses.CarSummarized;
import com.fresh.coding.carshow.dtos.responses.CarWithImageSummarized;
import com.fresh.coding.carshow.dtos.responses.Paginate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarService {
    List<CarSummarized> createAllCars(List<CarRequest> carRequests);

    List<CarWithImageSummarized> findAllCars();

    List<String> findAllBrandOfCars();

    List<CarSummarized> findAllCarByStatusPinned(Integer limit);

    List<String> findAllMotorTypeOfCars();

    List<String> findAllTypeOfCars();

    CarSummarized modifyCarById(Long id, CarRequest carRequest);

    CarSummarized deleteCarById(Long id);

    List<String> findAllColorsOfCars();

    Paginate<List<CarWithImageSummarized>> paginateCars(Integer page, Integer perPage);

    Paginate<List<CarWithImageSummarized>> findCarsByTypeAndExcludeId(String type, Long id, Integer page, Integer perPage);

    CarSummarized modifyStatusCarById(Long id, String status);

    CarWithImageSummarized createCarWithImage(CarRequest carRequest, MultipartFile[] files);

    List<String> findAllPowerOfCars();

    List<String> findAllModelOfCars();
}
