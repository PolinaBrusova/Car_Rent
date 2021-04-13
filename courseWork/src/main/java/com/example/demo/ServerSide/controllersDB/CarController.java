package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.ServerSide.repositories.CarRepository;
import com.example.demo.ServerSide.repositories.ComfortLevelRepository;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
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
    Car createCar(@RequestBody String line) {
        JSONObject jsonObject = new JSONObject(line);
        Car car = new Car();
        car.setBrand(jsonObject.getString("brand"));
        car.setCarcase(jsonObject.getString("carcase"));
        car.setColor(jsonObject.getString("color"));
        car.setDoorNumber(jsonObject.getInt("doorNumber"));
        car.setGearbox(jsonObject.getString("gearbox"));
        car.setReleaseYear(jsonObject.getInt("releaseYear"));
        car.setSeats(jsonObject.getInt("seats"));
        car.setComfortLevel(comfortLevelRepository.findComfortLevelById(jsonObject.getJSONObject("comfortLevel").getString("id")));
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

    @Transactional
    @DeleteMapping("/deleteCar={id}")
    public void deleteCar(@PathVariable Long id){
        this.carRepository.deleteCarById(id);
    }

    @Transactional
    @PutMapping("/updateCar")
    public Car updateCar(@RequestBody String line){
        JSONObject jsonObject = new JSONObject(line);
        Car current = this.carRepository.findCarById(jsonObject.getLong("id"));
        current.setId(jsonObject.getLong("id"));
        current.setBrand(jsonObject.getString("brand"));
        current.setCarcase(jsonObject.getString("carcase"));
        current.setColor(jsonObject.getString("color"));
        current.setDoorNumber(jsonObject.getInt("doorNumber"));
        current.setGearbox(jsonObject.getString("gearbox"));
        current.setReleaseYear(jsonObject.getInt("releaseYear"));
        current.setSeats(jsonObject.getInt("seats"));
        current.setComfortLevel(comfortLevelRepository.findComfortLevelById(new JSONObject(jsonObject.getString("comfortLevel")).getString("id")));
        return this.carRepository.save(current);
    }
}
