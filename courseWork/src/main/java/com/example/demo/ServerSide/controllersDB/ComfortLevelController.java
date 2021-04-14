package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.ComfortLevel;
import com.example.demo.ServerSide.repositories.ComfortLevelRepository;
import com.example.demo.utils.MyLogger;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tests")
public class ComfortLevelController {
    private final ComfortLevelRepository comfortLevelRepository;


    public ComfortLevelController(ComfortLevelRepository comfortLevelRepository) {
        this.comfortLevelRepository = comfortLevelRepository;
    }

    @GetMapping("/getComfortLevel/{id}")
    ComfortLevel getComfortLevel(@PathVariable String id) {
        MyLogger.inform("Найден уровень комфорта по id "+id);
        return this.comfortLevelRepository.findComfortLevelById(id);
    }

    @GetMapping("/AllComfortLevels")
    List<ComfortLevel> getAllComfortLevels() {
        MyLogger.inform("Выведен список всех уровней комфорта");
        return this.comfortLevelRepository.findAll();
    }

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
