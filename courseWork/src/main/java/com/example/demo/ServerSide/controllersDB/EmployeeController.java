package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Department;
import com.example.demo.ServerSide.models.Employee;
import com.example.demo.ServerSide.models.Position;
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

    @PostMapping("/addEmployee")
    Employee createEmployee(@RequestParam String firstName, @RequestParam String lastName,
                            @RequestParam String phone, @RequestParam String email,
                            @RequestParam String passport, @RequestParam String adress,
                            @RequestParam Department department, @RequestParam Position position,
                            @RequestParam int sales) {
        Employee employee = new Employee(firstName, lastName, phone, email, passport, adress, sales);
        employee.setDepartment(departmentRepository.findDepartmentById(department.getId()));
        employee.setPosition(positionRepository.findPositionById(position.getId()));
        return this.employeeRepository.save(employee);
    }

    @GetMapping("/getEmployee={id}")
    Employee getEmployee(@PathVariable Long id) {
        return this.employeeRepository.findEmployeeById(id);
    }

    @GetMapping("/positionById={id}")
    String getPositionById(@PathVariable Long id) {
        return this.positionRepository.findPositionById(this.employeeRepository.findEmployeeById(id).getPosition().getId()).toString();
    }
}
