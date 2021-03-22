package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.ComfortLevel;
import com.example.demo.ServerSide.repositories.ComfortLevelRepository;
import org.springframework.web.bind.annotation.*;

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
}
