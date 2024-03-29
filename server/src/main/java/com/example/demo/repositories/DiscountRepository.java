package com.example.demo.repositories;

import com.example.demo.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface that extends JpaRepository of Discount, Integer
 */
@Repository
@Service
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    /**
     * Finds all rows from the table
     * @return List of Discount with all found rows
     */
    List<Discount> findAll();

    /**
     * Finds a Department by its id from the table
     * @param id String value of the id
     * @return Discount object found (or null - if not found)
     */
    Discount findDiscountById(String id);
}
