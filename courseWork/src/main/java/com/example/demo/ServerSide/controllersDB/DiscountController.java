package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Discount;
import com.example.demo.ServerSide.repositories.DiscountRepository;
import com.example.demo.utils.MyLogger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class DiscountController {
    private final DiscountRepository discountRepository;


    public DiscountController(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @GetMapping("/getDiscount/{id}")
    Discount getDiscount(@PathVariable String id) {
        MyLogger.inform("Получена скидка по id "+id);
        return this.discountRepository.findDiscountById(id);
    }
}
