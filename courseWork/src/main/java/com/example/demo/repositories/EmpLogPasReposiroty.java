package com.example.demo.repositories;

import com.example.demo.models.EmpLogPas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpLogPasReposiroty extends JpaRepository<EmpLogPas, Integer> {
    List<EmpLogPas> findAll();
    EmpLogPas findEmpLogPasById(long id);
}
