package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Position;
import com.example.demo.ServerSide.repositories.PositionRepository;
import com.example.demo.utils.MyLogger;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for the database
 */
@RestController
@RequestMapping("api/tests")
public class PositionController {
    private final PositionRepository positionRepository;


    /**
     * Initialises the controller and assigns the private variables
     * @param positionRepository PositionRepository - JPA Repository child
     */
    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    /**
     * Finds a Position by it's id in the database via the PositionRepository - JPA Repository child
     * This method always returns immediately, whether or not the Position exists in the database.
     * @param id Long value of the Position id in the database
     * @return Position object
     */
    @GetMapping("/getPosition={id}")
    Position getPosition(@PathVariable Long id) {
        MyLogger.inform("Выведена должность по id "+id);
        return this.positionRepository.findPositionById(id);
    }
}
