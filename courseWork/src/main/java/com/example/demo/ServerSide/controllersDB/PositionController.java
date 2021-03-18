package com.example.demo.ServerSide.controllersDB;

import com.example.demo.models.Position;
import com.example.demo.ServerSide.repositories.PositionRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class PositionController {
    private final PositionRepository positionRepository;


    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @PostMapping("/positions")
    Position createPosition(@RequestParam String name, @RequestParam Integer salary,
                            @RequestParam(required = false) Integer numberOfDaysOff,
                            @RequestParam(required = false) Integer numberOfWorkDays) {
//        curl -X POST http://127.0.0.1:8080/api/theater/ticket?price=777
        Position position = new Position(name, salary, numberOfDaysOff, numberOfWorkDays);
        return this.positionRepository.save(position);
    }

    @GetMapping("/positions/{id}")
    Position getPosition(@PathVariable Long id) {
//        curl -X GET http://127.0.0.1:8080/api/theater/ticket?price=777
        return this.positionRepository.findPositionById(id);
    }
}
