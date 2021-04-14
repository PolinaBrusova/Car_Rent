package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Position;
import com.example.demo.ServerSide.repositories.PositionRepository;
import com.example.demo.utils.MyLogger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class PositionController {
    private final PositionRepository positionRepository;


    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @GetMapping("/getPosition={id}")
    Position getPosition(@PathVariable Long id) {
        MyLogger.inform("Выведена должность по id "+id);
        return this.positionRepository.findPositionById(id);
    }
}
