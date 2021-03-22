package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Position;
import com.example.demo.ServerSide.repositories.PositionRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class PositionController {
    private final PositionRepository positionRepository;


    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @PostMapping("/addPosition")
    Position createPosition(@RequestParam String name, @RequestParam Integer salary,
                            @RequestParam(required = false) Integer numberOfDaysOff,
                            @RequestParam(required = false) Integer numberOfWorkDays) {
        Position position = new Position(name, salary, numberOfDaysOff, numberOfWorkDays);
        return this.positionRepository.save(position);
    }

    @GetMapping("/getPosition={id}")
    Position getPosition(@PathVariable Long id) {
        return this.positionRepository.findPositionById(id);
    }
}
