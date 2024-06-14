package com.fresh.coding.carshow.repositories;

import com.fresh.coding.carshow.entities.Car;
import com.fresh.coding.carshow.enums.CarStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Page<Car> findByStatus(CarStatus status, Pageable pageable);

    @Query("SELECT DISTINCT c.motorType FROM Car c")
    List<String> findAllMotorTypeOfCars();

    @Query("SELECT DISTINCT c.type FROM Car c")
    List<String> findAllTypeOfCars();

    @Query("SELECT DISTINCT c.color FROM Car c")
    List<String> findAllColors();

    @Query("SELECT c FROM Car c WHERE c.type = :type AND c.id <> :id")
    Page<Car> findAllByTypeAndExcludeId(@Param("type") String type, @Param("id") Long id, Pageable pageable);


    @Query("""
              SELECT c FROM Car c
              WHERE LOWER(c.brand) LIKE LOWER(CONCAT('%', :brand, '%'))
                AND LOWER(c.model) LIKE LOWER(CONCAT('%', :model, '%'))
                AND LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))
                AND LOWER(c.type) LIKE LOWER(CONCAT('%', :type, '%'))
                AND c.price BETWEEN :priceMin AND :priceMax
            """)
    List<Car> findCarsWithCriteria(
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("name") String name,
            @Param("type") String type,
            @Param("priceMin") Long priceMin,
            @Param("priceMax") Long priceMax
    );


    List<Car> findAllByModelContainingIgnoreCase(String model);

    List<Car> findAllByBrandContainingIgnoreCase(String brand);

    List<Car> findAllByBrandContainingIgnoreCaseAndModelContainingIgnoreCase(String brand, String model);

    List<Car> findAllByPriceBetween(Long minPrice, Long maxPrice);

    List<Car> findAllByMotorTypeContainingIgnoreCase(String typeMotor);

    List<Car> findAllByTypeContainingIgnoreCase(String type);

    List<Car> findAllByPriceGreaterThanEqual(Long price);

    List<Car> findAllByPriceLessThanEqual(Long price);

    @Query("SELECT DISTINCT c.brand FROM Car c")
    List<String> findAllBrandOfCars();

    @Query("SELECT DISTINCT c.model FROM Car c")
    List<String> findAllModelOfCars();

    @Query("SELECT DISTINCT c.power FROM Car c")
    List<String> findAllPowerOfCars();
}
