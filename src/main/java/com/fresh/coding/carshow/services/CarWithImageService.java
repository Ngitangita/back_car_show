package com.fresh.coding.carshow.services;

import com.fresh.coding.carshow.dtos.responses.CarWithImageSummarized;

public interface CarWithImageService {
    CarWithImageSummarized findCarWithImage(Long id);
}
