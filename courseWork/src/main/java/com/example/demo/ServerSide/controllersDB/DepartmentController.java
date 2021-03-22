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

    @PostMapping("/addDepartment")
    Department createDepartment(@RequestParam String adress, @RequestParam String head,
                                @RequestParam String phone, @RequestParam String email,
                                @RequestParam String additional) {
        Department department = new Department(adress,head,phone,email,additional);
        return this.departmentRepository.save(department);
    }

    @GetMapping("/getDepartment={id}")
    Department getDepartmant(@PathVariable Long id) {
        return this.departmentRepository.findDepartmentById(id);
    }
}