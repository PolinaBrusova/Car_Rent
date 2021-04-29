package com.example.demo.controllers;

import com.example.demo.models.Discount;
import com.example.demo.repositories.DiscountRepository;
import com.example.demo.utils.MyLogger;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Controller for the database
 */
@RestController
@RequestMapping("api/tests")
public class DiscountController {
    private final DiscountRepository discountRepository;

    /**
     * Initialises the controller and assigns the private variables
     * @param discountRepository DiscountRepository - JPA Repository child
     */
    public DiscountController(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    /**
     * Finds a Discount by it's id in the database via the DiscountRepository - JPA Repository child
     * This method always returns immediately, whether or not the discount exists in the database.
     * @param id String value of the Discount id in the database
     * @return Discount object
     */
    @GetMapping("/getDiscount/{id}")
    Discount getDiscount(@PathVariable String id) {
        MyLogger.inform("Получена скидка по id "+id);
        return this.discountRepository.findDiscountById(id);
    }
}
