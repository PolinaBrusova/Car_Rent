package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.ComfortLevel;
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
public class ComfortLevelController {
    private final ComfortLevelRepository comfortLevelRepository;

    /**
     * Initialises the controller and assigns the private variables
     * @param comfortLevelRepository ComfortLevelRepository - JPA Repository child
     */
    public ComfortLevelController(ComfortLevelRepository comfortLevelRepository) {
        this.comfortLevelRepository = comfortLevelRepository;
    }

    /**
     * Finds a ComfortLevel by it's id in the database via the ComfortLevelRepository - JPA Repository child
     * This method always returns immediately, whether or not the comfort level exists in the database.
     * @param id String value of the ComfortLevel id in the database
     * @return ComfortLevel object
     */
    @GetMapping("/getComfortLevel/{id}")
    ComfortLevel getComfortLevel(@PathVariable String id) {
        MyLogger.inform("Найден уровень комфорта по id "+id);
        return this.comfortLevelRepository.findComfortLevelById(id);
    }

    /**
     * Finds all Comfort Levels that exist in the database via the ComfortLevelRepository - JPA Repository child
     * This method always returns immediately, whether or not the database has any rows.
     * @return List of ComfortLevel objects
     */
    @GetMapping("/AllComfortLevels")
    List<ComfortLevel> getAllComfortLevels() {
        MyLogger.inform("Выведен список всех уровней комфорта");
        return this.comfortLevelRepository.findAll();
    }

    /**
     * Updates the fields of the ComfortLevel via the ComfortLevelRepository - JPA Repository child
     * The line must specify all field that are mentioned as not null in the ComfortLevel class AND the id
     * @param line String that contains JSON with the class fields
     * @return updated ComfortLevel object
     */
    @Transactional
    @PutMapping("/updateComfortLevel")
    public ComfortLevel updateComfortLevel(@RequestBody String line){
        JSONObject jsonObject = new JSONObject(line);
        ComfortLevel current = this.comfortLevelRepository.findComfortLevelById(jsonObject.getString("id"));
        current.setId(jsonObject.getString("id"));
        current.setLevel(jsonObject.getString("level"));
        current.setDeposit(jsonObject.getLong("deposit"));
        current.setRentPrice(jsonObject.getLong("rentPrice"));
        current.setMinExperience(jsonObject.getInt("minExperience"));
        MyLogger.inform("Перезаписан уровень комфорта по id "+current.getId());
        return this.comfortLevelRepository.save(current);
    }
}
