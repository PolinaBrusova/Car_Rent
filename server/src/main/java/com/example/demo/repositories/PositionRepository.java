package com.example.demo.repositories;

import com.example.demo.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface that extends JpaRepository of Position, Integer
 */
@Repository
@Service
public interface PositionRepository extends JpaRepository<Position, Integer> {
    /**
     * Finds all rows from the table
     * @return List of Position with all found rows
     */
    List<Position> findAll();

    /**
     * Finds a Position by its id from the table
     * @param id Long value of the id
     * @return Position object found (or null - if not found)
     */
    Position findPositionById(Long id);
}
