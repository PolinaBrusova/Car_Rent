package com.example.demo.ServerSide.repositories;

import com.example.demo.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Service
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    public List<Employee> findAll();

    public Employee findEmployeeById(Long id);
}
