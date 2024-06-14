package com.fresh.coding.carshow.services;

import com.fresh.coding.carshow.dtos.responses.ImageSummarized;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<ImageSummarized> createImage(Long carId, MultipartFile[] files);

    List<ImageSummarized> findAllImages();

    ImageSummarized updateImage(Long id, Long carId, MultipartFile file);

    List<ImageSummarized> deleteImage(Long carId);

    List<ImageSummarized> findImage(Long carId);
}
