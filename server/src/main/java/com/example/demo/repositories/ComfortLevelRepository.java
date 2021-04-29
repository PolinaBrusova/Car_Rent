package com.example.demo.repositories;

import com.example.demo.models.ComfortLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface that extends JpaRepository<ComfortLevel, Integer>
 */
@Repository
public interface ComfortLevelRepository extends JpaRepository<ComfortLevel, Integer> {

    /**
     * Finds a Client by its id from the table
     * @param id String value of the id
     * @return ComfortLevel object found (or null - if not found)
     */
    ComfortLevel findComfortLevelById(String id);

    /**
     * Finds all rows from the table
     * @return List<ComfortLevel> with all found rows
     */
    List<ComfortLevel> findAll();
}
