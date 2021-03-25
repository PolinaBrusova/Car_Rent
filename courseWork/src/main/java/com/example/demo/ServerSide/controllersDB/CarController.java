package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.ServerSide.models.ComfortLevel;
import com.example.demo.ServerSide.repositories.CarRepository;
import com.example.demo.ServerSide.repositories.ComfortLevelRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/addCar")
    Car createCar(@RequestParam String brand, @RequestParam String carcase, @RequestParam String gearbox,
                  @RequestParam Integer doorNumber, @RequestParam Integer seats, @RequestParam Integer releaseYear,
                  @RequestParam String color) {
//        curl -X POST http://127.0.0.1:8080/api/theater/ticket?price=777
        Car car = new Car(brand, carcase, gearbox, doorNumber, seats, releaseYear, color);
        car.setComfortLevel(comfortLevelRepository.findComfortLevelById("A"));
        return this.carRepository.save(car);
    }

    @GetMapping("/getCar={id}")
    Car getCar(@PathVariable Long id) {
        return this.carRepository.findCarById(id);
    }

    @GetMapping("/AllCars")
    List<Car> getAllCars() {
        return this.carRepository.findAll();
    }

    @GetMapping("/LevelByCarId={id}")
    String getLevelByCarId(@PathVariable Long id) {
        return this.comfortLevelRepository.findComfortLevelById(this.carRepository.findCarById(id).getComfortLevel().getId()).toString();
    }
}