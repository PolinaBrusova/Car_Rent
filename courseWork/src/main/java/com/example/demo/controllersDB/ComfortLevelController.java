package com.example.demo.controllersDB;

import com.example.demo.models.ComfortLevel;
import com.example.demo.repositories.ComfortLevelRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class ComfortLevelController {
    private final ComfortLevelRepository comfortLevelRepository;


    public ComfortLevelController(ComfortLevelRepository comfortLevelRepository) {
        this.comfortLevelRepository = comfortLevelRepository;
    }

    @PostMapping("/comfort_levels")
    ComfortLevel createComfortLevel(@RequestParam String id, @RequestParam String level, @RequestParam Long deposit,
                           @RequestParam Long rentPrice, @RequestParam Integer minExperience) {
//        curl -X POST http://127.0.0.1:8080/api/theater/ticket?price=777
        ComfortLevel comfortLevel = new ComfortLevel(id, level, deposit, rentPrice, minExperience);
        return this.comfortLevelRepository.save(comfortLevel);
    }

    @GetMapping("/comfort_levels/{id}")
    ComfortLevel getComfortLevel(@PathVariable String id) {
//        curl -X GET http://127.0.0.1:8080/api/theater/ticket?price=777
        return this.comfortLevelRepository.findComfortLevelById(id);
    }
}
