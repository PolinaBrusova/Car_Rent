package com.example.demo.ServerSide.controllersDB;

import com.example.demo.models.Department;
import com.example.demo.models.Employee;
import com.example.demo.models.Position;
import com.example.demo.ServerSide.repositories.DepartmentRepository;
import com.example.demo.ServerSide.repositories.EmployeeRepository;
import com.example.demo.ServerSide.repositories.PositionRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;


    public EmployeeController(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
                              PositionRepository positionRepository) {
        this.employeeRepository=employeeRepository;
        this.departmentRepository=departmentRepository;
        this.positionRepository=positionRepository;
    }

    @PostMapping("/employees")
    Employee createEmployee(@RequestParam String firstName, @RequestParam String lastName,
                            @RequestParam String phone, @RequestParam String email,
                            @RequestParam String passport, @RequestParam String adress,
                            @RequestParam Department department, @RequestParam Position position) {
//        curl -X POST http://127.0.0.1:8080/api/theater/ticket?price=777
        Employee employee = new Employee(firstName, lastName, phone, email, passport, adress);
        employee.setDepartment(departmentRepository.findDepartmentById(department.getId()));
        employee.setPosition(positionRepository.findPositionById(position.getId()));
        return this.employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    Employee getEmployee(@PathVariable Long id) {
//        curl -X GET http://127.0.0.1:8080/api/theater/ticket?price=777
        return this.employeeRepository.findEmployeeById(id);
    }
}
