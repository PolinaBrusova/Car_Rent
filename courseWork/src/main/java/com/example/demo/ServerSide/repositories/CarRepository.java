package com.example.demo.ServerSide.repositories;

import com.example.demo.ServerSide.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    public List<Car> findAll();

    public Car findCarById(Long id);

    public int deleteCarById(Long id);

    List<Car> findCarsByGearboxAndAndDoorNumberAndSeatsAndAvailable(String gearbox, int doorNumber, int seats, boolean available);
}