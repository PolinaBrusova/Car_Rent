package com.example.demo.repositories;

import com.example.demo.models.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface that extends JpaRepository<Rent, Integer>
 */
@Repository
@Service
public interface RentRepository extends JpaRepository<Rent, Integer> {

    /**
     * Finds all Rents by Car's id from the table
     * @param id Long value of the id
     * @return List<Rent> with found rents (or null - if not found)
     */
    List<Rent> findRentsByCarId(Long id);

    /**
     * Finds all rows from the table
     * @return List<Rent> with all found rows
     */
    List<Rent> findAll();

    /**
     * Finds a Rent by its id from the table
     * @param id Long value of the id
     * @return Rent object found (or null - if not found)
     */
    Rent findRentById(Long id);

    /**
     * Finds all Rents by Client's id from the table
     * @param id Long value of the id
     * @return List<Rent> with found rents (or null - if not found)
     */
    List<Rent> findRentsByClientId(Long id);
}
