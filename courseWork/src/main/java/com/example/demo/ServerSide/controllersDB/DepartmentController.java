package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Department;
import com.example.demo.ServerSide.repositories.DepartmentRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;


    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository=departmentRepository;
    }

    @GetMapping("/getDepartment={id}")
    Department getDepartmant(@PathVariable Long id) {
        return this.departmentRepository.findDepartmentById(id);
    }
}