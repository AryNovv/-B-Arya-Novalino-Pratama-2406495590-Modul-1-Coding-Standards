package com.example.eshop.repository;

import com.example.eshop.model.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CarRepository {

    private final List<Car> carData = new ArrayList<>();

    public Car create(Car car) {
        if (car.getCarId() == null) {
            car.setCarId(UUID.randomUUID().toString());
        }
        carData.add(car);
        return car;
    }

    public List<Car> findAll() {
        return new ArrayList<>(carData);
    }

    public Car findById(String id) {
        return carData.stream()
                .filter(car -> car.getCarId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Car update(String id, Car updatedCar) {
        Car existingCar = findById(id);
        if (existingCar != null) {
            existingCar.setCarName(updatedCar.getCarName());
            existingCar.setCarColor(updatedCar.getCarColor());
            existingCar.setCarQuantity(updatedCar.getCarQuantity());
        }
        return existingCar;
    }

    public void delete(String id) {
        carData.removeIf(car -> car.getCarId().equals(id));
    }
}