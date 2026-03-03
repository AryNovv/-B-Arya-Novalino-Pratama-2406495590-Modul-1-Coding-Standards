package com.example.eshop.service;

import com.example.eshop.model.Car;

import java.util.List;

public interface CarService {

    Car create(Car car);

    List<Car> findAll();

    Car findById(String carId);

    void update(String carId, Car car);

    void deleteCarById(String carId);
}