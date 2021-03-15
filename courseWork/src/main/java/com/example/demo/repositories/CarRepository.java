package com.example.demo.repositories;

import com.example.demo.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    public List<Car> findAll();

    public Car findCarById(Long id);
}