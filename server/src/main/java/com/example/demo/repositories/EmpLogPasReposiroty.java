package com.example.demo.repositories;

import com.example.demo.models.EmpLogPas;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Interface that extends JpaRepository of EmpLogPas, Integer
 */
public interface EmpLogPasReposiroty extends JpaRepository<EmpLogPas, Integer> {

    /**
     * Finds an EmpLogPas by its id from the table
     * @param id Long value of the id
     * @return EmpLogPas object found (or null - if not found)
     */
    EmpLogPas findEmpLogPasById(long id);
}
