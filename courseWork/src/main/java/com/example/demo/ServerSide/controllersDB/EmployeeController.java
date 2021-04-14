package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Department;
import com.example.demo.ServerSide.models.Employee;
import com.example.demo.ServerSide.models.Position;
import com.example.demo.ServerSide.repositories.DepartmentRepository;
import com.example.demo.ServerSide.repositories.EmployeeRepository;
import com.example.demo.ServerSide.repositories.PositionRepository;
import com.example.demo.utils.MyLogger;
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

    @GetMapping("/getEmployee={id}")
    Employee getEmployee(@PathVariable Long id) {
        MyLogger.inform("Выведен работник по id "+id);
        return this.employeeRepository.findEmployeeById(id);
    }

    @GetMapping("/positionById={id}")
    Position getPositionById(@PathVariable Long id) {
        MyLogger.inform("Выведена должность работника по его id "+id);
        return this.positionRepository.findPositionById(this.employeeRepository.findEmployeeById(id).getPosition().getId());
    }

    @GetMapping("/departmentById={id}")
    Department getDepartmentById(@PathVariable Long id) {
        MyLogger.inform("Выведен департамент работника по его id "+id);
        return this.departmentRepository.findDepartmentById(this.employeeRepository.findEmployeeById(id).getDepartment().getId());
    }
}
