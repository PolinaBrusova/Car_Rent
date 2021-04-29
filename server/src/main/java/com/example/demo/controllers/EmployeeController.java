package com.example.demo.controllers;

import com.example.demo.models.Employee;
import com.example.demo.models.Position;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.PositionRepository;
import com.example.demo.utils.MyLogger;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for the database
 */
@RestController
@RequestMapping("api/tests")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;

    /**
     * Initialises the controller and assigns the private variables
     * @param employeeRepository EmployeeRepository - JPA Repository child
     * @param positionRepository PositionRepository - JPA Repository child
     */
    public EmployeeController(EmployeeRepository employeeRepository, PositionRepository positionRepository) {
        this.employeeRepository=employeeRepository;
        this.positionRepository=positionRepository;
    }

    /**
     * Finds an Employee by it's id in the database via the EmployeeRepository - JPA Repository child
     * This method always returns immediately, whether or not the Employee exists in the database.
     * @param id Long value of the Employee id in the database
     * @return Employee object
     */
    @GetMapping("/getEmployee={id}")
    Employee getEmployee(@PathVariable Long id) {
        MyLogger.inform("Выведен работник по id "+id);
        return this.employeeRepository.findEmployeeById(id);
    }

    /**
     * Finds a Position by Employee's id in the database via the EmployeeRepository
     * and the PositionRepository - JPA Repository child
     * This method always returns immediately, whether or not the Employee exists in the database.
     * @param id Long value of the Employee id in the database
     * @return Position object that was assigned to the Employee
     */
    @GetMapping("/positionById={id}")
    Position getPositionById(@PathVariable Long id) {
        MyLogger.inform("Выведена должность работника по его id "+id);
        return this.positionRepository.findPositionById(this.employeeRepository.findEmployeeById(id).getPosition().getId());
    }
}
