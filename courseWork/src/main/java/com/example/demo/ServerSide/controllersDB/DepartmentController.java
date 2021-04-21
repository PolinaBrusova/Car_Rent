package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Department;
import com.example.demo.ServerSide.repositories.DepartmentRepository;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for the database
 */
@RestController
@RequestMapping("api/tests")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;

    /**
     * Initialises the controller and assigns the private variables
     * @param departmentRepository DepartmentRepository - JPA Repository child
     */
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository=departmentRepository;
    }

    /**
     * Finds a Department by it's id in the database via the DepartmentRepository - JPA Repository child
     * This method always returns immediately, whether or not the Department exists in the database.
     * @param id Long value of the Department id in the database
     * @return Department object
     */
    @GetMapping("/getDepartment={id}")
    Department getDepartmant(@PathVariable Long id) {
        return this.departmentRepository.findDepartmentById(id);
    }
}