package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.ServerSide.models.ComfortLevel;
import com.example.demo.ServerSide.repositories.ComfortLevelRepository;
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

    @PostMapping("/addComfortLevel")
    ComfortLevel createComfortLevel(@RequestParam String id, @RequestParam String level, @RequestParam Long deposit,
                           @RequestParam Long rentPrice, @RequestParam Integer minExperience) {
        ComfortLevel comfortLevel = new ComfortLevel(id, level, deposit, rentPrice, minExperience);
        return this.comfortLevelRepository.save(comfortLevel);
    }

    @GetMapping("/getComfortLevel/{id}")
    ComfortLevel getComfortLevel(@PathVariable String id) {
        return this.comfortLevelRepository.findComfortLevelById(id);
    }

    @GetMapping("/AllComfortLevels")
    List<ComfortLevel> getAllComfortLevels() {
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
        return this.comfortLevelRepository.save(current);
    }
}
