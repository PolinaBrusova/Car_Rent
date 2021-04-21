package com.example.demo.ServerSide.repositories;

import com.example.demo.ServerSide.models.EmpLogPas;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Interface that extends JpaRepository<EmpLogPas, Integer>
 */
public interface EmpLogPasReposiroty extends JpaRepository<EmpLogPas, Integer> {

    /**
     * Finds an EmpLogPas by its id from the table
     * @param id Long value of the id
     * @return EmpLogPas object found (or null - if not found)
     */
    EmpLogPas findEmpLogPasById(long id);
}
