package com.fresh.coding.carshow.services.impl;

import com.fresh.coding.carshow.dtos.responses.ImageSummarized;
import com.fresh.coding.carshow.entities.Car;
import com.fresh.coding.carshow.entities.Image;
import com.fresh.coding.carshow.exceptions.InternalServerException;
import com.fresh.coding.carshow.exceptions.NotFoundException;
import com.fresh.coding.carshow.files.FileService;
import com.fresh.coding.carshow.mappers.ImageMapper;
import com.fresh.coding.carshow.repositories.CarRepository;
import com.fresh.coding.carshow.repositories.ImageRepository;
import com.fresh.coding.carshow.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final FileService fileService;
    private final ImageMapper imageMapper;
    private final CarRepository carRepository;


    @Transactional
    @Override
    public List<ImageSummarized> createImage(Long carId, MultipartFile[] files) {
        var car = carRepository.findById(carId).orElseThrow(() -> new NotFoundException(String.format("Car with id %d not found", carId)));
        var imageSummarizedList = new ArrayList<ImageSummarized>();
        for (MultipartFile file : files) {
            imageSummarizedList.add(saveImage(car, file));
        }

        return imageSummarizedList;
    }

    @Transactional
    protected ImageSummarized saveImage(Car car, MultipartFile file) {
        var url = fileService.saveFile(file);
        var image = Image.builder().url(url).car(car).build();
        var imageSaved = imageRepository.save(image);
        return imageMapper.toResponse(imageSaved);
    }

    @Override
    public List<ImageSummarized> findAllImages() {
        return imageRepository.findAll()
                .stream().map(imageMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ImageSummarized updateImage(Long id, Long carId, MultipartFile file) {
        if (!carRepository.existsById(carId)) {
            throw new NotFoundException(String.format("Car with id %d not found", carId));
        }
        var image = imageRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Image with id %d not found", id)));
        var isDeleted = fileService.deleteFile(image.getUrl());
        if (isDeleted) {
            String url = fileService.saveFile(file);
            image.setUrl(url);
            var savedImage = imageRepository.save(image);
            return imageMapper.toResponse(savedImage);
        }
        throw new InternalServerException("Error of the removed image");
    }

    @Transactional
    @Override
    public List<ImageSummarized> deleteImage(Long carId) {
        var images = imageRepository.findByCar_Id(carId);
        var res = new ArrayList<ImageSummarized>();
        for (var image : images) {
            fileService.deleteFile(image.getUrl());
            var saved = imageMapper.toResponse(image);
            res.add(saved);
        }
        imageRepository.deleteAll(images);
        return res;
    }

    @Override
    public List<ImageSummarized> findImage(Long carId) {
        return imageRepository.findByCar_Id(carId)
                .stream().map(imageMapper::toResponse)
                .collect(Collectors.toList());
    }

}
