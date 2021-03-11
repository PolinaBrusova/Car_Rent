package com.example.demo.repositories;

import com.example.demo.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Service
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    public List<Department> findAll();
}
