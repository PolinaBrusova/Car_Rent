package com.example.demo.repositories;

import com.example.demo.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface that extends JpaRepository<Car, Integer>
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    /**
     * Finds all rows from the table
     * @return List<Car> with all found rows
     */
    List<Car> findAll();

    /**
     * Finds a Car by its id from the table
     * @param id Long value of the id
     * @return Car object found (or null - if not found)
     */
    Car findCarById(Long id);

    /**
     * Deletes a Car by its id from the table
     * @param id Long value of the id
     */
    void deleteCarById(Long id);

    /**
     * Find all Cars that have appropriate fields from the database
     * @param gearbox String value of the gearbox
     * @param doorNumber Integer value of the doorNumber
     * @param seats Integer value of the seats
     * @param available boolean value that equals true
     * @return List<Car> with all found rows
     */
    List<Car> findCarsByGearboxAndAndDoorNumberAndSeatsAndAvailable(String gearbox, int doorNumber,
                                                                    int seats, boolean available);
}