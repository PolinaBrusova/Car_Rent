package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.ServerSide.repositories.CarRepository;
import com.example.demo.ServerSide.repositories.ComfortLevelRepository;
import com.example.demo.utils.MyLogger;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller for the database
 */
@RestController
@RequestMapping("api/tests")
public class CarController {

    private final CarRepository carRepository;
    private final ComfortLevelRepository comfortLevelRepository;

    /**
     * Initialises the controller and assigns the private variables
     * @param carRepository CarRepository - JPA Repository child
     * @param comfortLevelRepository ComfortLevelRepository - JPA Repository child
     */
    public CarController(CarRepository carRepository, ComfortLevelRepository comfortLevelRepository) {
        this.carRepository = carRepository;
        this.comfortLevelRepository = comfortLevelRepository;
    }

    /**
     * Saves a car object to the database via the CarRepository - JPA Repository child
     * The line must specify all field that are mentioned as not null in the car class except the id
     * @param line String that contains JSON with the class fields
     * @return Car object that has been saved
     */
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
        MyLogger.inform("Успешно добавлена машина");
        return this.carRepository.save(car);
    }

    /**
     * Finds a car by it's id in the database via the CarRepository - JPA Repository child
     * This method always returns immediately, whether or not the car exists in the database.
     * @param id Long value of the Car id in the database
     * @return Car object
     */
    @GetMapping("/getCar={id}")
    Car getCar(@PathVariable Long id) {
        MyLogger.inform("Найдена машина по id "+id);
        return this.carRepository.findCarById(id);
    }

    /**
     * Finds all Cars that exist in the database via the CarRepository - JPA Repository child
     * This method always returns immediately, whether or not the database has any rows.
     * @return List of Car objects
     */
    @GetMapping("/AllCars")
    List<Car> getAllCars() {
        MyLogger.inform("Выведен список всех машин");
        return this.carRepository.findAll();
    }

    /**
     * Finds a ComfortLevel by Car's id in the database via the CarRepository
     * and the ComfortLevelRepository - JPA Repository child
     * This method always returns immediately, whether or not the car exists in the database.
     * @param id Long value of the Car id in the database
     * @return String Comfort level id that was assigned to the Car
     */
    @GetMapping("/LevelByCarId={id}")
    String getLevelByCarId(@PathVariable Long id) {
        MyLogger.inform("Найдена уровень комфорта по id машины "+id);
        return this.comfortLevelRepository.findComfortLevelById(this.carRepository.findCarById(id).getComfortLevel().getId()).toString();
    }

    /**
     * Deletes the Car by it's id from the database via the CarRepository - JPA Repository child
     * @param id Long value of the Car id in the database
     */
    @Transactional
    @DeleteMapping("/deleteCar={id}")
    public void deleteCar(@PathVariable Long id){
        MyLogger.inform("Удалена машина по id "+id);
        this.carRepository.deleteCarById(id);
    }

    /**
     * Updates the fields of the Car via the CarRepository - JPA Repository child
     * The line must specify all field that are mentioned as not null in the car class AND the id
     * @param line String that contains JSON with the class fields
     * @return updated Car object
     */
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
        MyLogger.inform("Перезаписана машина по id "+current.getId());
        return this.carRepository.save(current);
    }
}
