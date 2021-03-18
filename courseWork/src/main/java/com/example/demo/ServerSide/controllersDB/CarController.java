package com.example.demo.ServerSide.controllersDB;

import com.example.demo.models.Car;
import com.example.demo.ServerSide.repositories.CarRepository;
import com.example.demo.ServerSide.repositories.ComfortLevelRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class CarController {

    private final CarRepository carRepository;
    private final ComfortLevelRepository comfortLevelRepository;


    public CarController(CarRepository carRepository,
                         ComfortLevelRepository comfortLevelRepository) {
        this.carRepository = carRepository;
        this.comfortLevelRepository = comfortLevelRepository;
    }

    @PostMapping("/cars")
    Car createCar(@RequestParam String brand, @RequestParam String carcase, @RequestParam String gearbox,
                  @RequestParam Integer doorNumber, @RequestParam Integer seats, @RequestParam Integer releaseYear,
                  @RequestParam String color) {
//        curl -X POST http://127.0.0.1:8080/api/theater/ticket?price=777
        Car car = new Car(brand, carcase, gearbox, doorNumber, seats, releaseYear, color);
        car.setComfortLevel(comfortLevelRepository.findComfortLevelById("A"));
        return this.carRepository.save(car);
    }

    @GetMapping("/cars/{id}")
    Car getCar(@PathVariable Long id) {
//        curl -X GET http://127.0.0.1:8080/api/theater/ticket?price=777
        return this.carRepository.findCarById(id);
    }
}
