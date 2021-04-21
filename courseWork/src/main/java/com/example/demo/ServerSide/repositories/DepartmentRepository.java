package com.example.demo.ServerSide.repositories;

import com.example.demo.ServerSide.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface that extends JpaRepository<Department, Integer>
 */
@Repository
@Service
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    /**
     * Finds all rows from the table
     * @return List<Department> with all found rows
     */
    List<Department> findAll();

    /**
     * Finds a Department by its id from the table
     * @param id Long value of the id
     * @return Department object found (or null - if not found)
     */
    Department findDepartmentById(Long id);
}
