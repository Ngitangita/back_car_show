package com.fresh.coding.carshow.repositories;

import com.fresh.coding.carshow.dtos.responses.ImageSummarized;
import com.fresh.coding.carshow.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("SELECT new com.fresh.coding.carshow.dtos.responses.ImageSummarized(i.id, i.url) FROM Image  i WHERE i.car.id = ?1")
    List<ImageSummarized> findAllByCarId(Long carId);

    List<Image> findByCar_Id(Long id);
}
