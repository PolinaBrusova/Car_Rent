package com.example.demo.repositories;

import com.example.demo.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Service
public interface CarRepository extends JpaRepository<Car, Integer> {
    public List<Car> findAll();
}