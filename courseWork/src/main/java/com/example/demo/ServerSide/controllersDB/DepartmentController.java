package com.example.demo.ServerSide.controllersDB;

import com.example.demo.models.Department;
import com.example.demo.ServerSide.repositories.DepartmentRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;


    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository=departmentRepository;
    }

    @PostMapping("/departments")
    Department createDepartment(@RequestParam String adress, @RequestParam String head,
                                @RequestParam String phone, @RequestParam String email,
                                @RequestParam String additional) {
//        curl -X POST http://127.0.0.1:8080/api/theater/ticket?price=777
        Department department = new Department(adress,head,phone,email,additional);
        return this.departmentRepository.save(department);
    }

    @GetMapping("/departments/{id}")
    Department getDepartmant(@PathVariable Long id) {
//        curl -X GET http://127.0.0.1:8080/api/theater/ticket?price=777
        return this.departmentRepository.findDepartmentById(id);
    }
}