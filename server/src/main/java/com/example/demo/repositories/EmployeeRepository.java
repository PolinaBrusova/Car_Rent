package com.example.demo.repositories;

import com.example.demo.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface that extends JpaRepository<Employee, Integer>
 */
@Repository
@Service
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /**
     * Finds all rows from the table
     * @return List<Employee> with all found rows
     */
    List<Employee> findAll();

    /**
     * Finds an Employee by its id from the table
     * @param id Long value of the id
     * @return Employee object found (or null - if not found)
     */
    Employee findEmployeeById(Long id);
}
