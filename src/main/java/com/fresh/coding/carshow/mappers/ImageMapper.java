package com.fresh.coding.carshow.mappers;

import com.fresh.coding.carshow.dtos.responses.ImageSummarized;
import com.fresh.coding.carshow.entities.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public ImageSummarized toResponse(Image image) {
        return new ImageSummarized(image.getId(), image.getUrl());
    }
}
