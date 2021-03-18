package com.example.demo.ServerSide.controllersDB;

import com.example.demo.models.Discount;
import com.example.demo.ServerSide.repositories.DiscountRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class DiscountController {
    private final DiscountRepository discountRepository;


    public DiscountController(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @PostMapping("/discounts")
    Discount createDiscount(@RequestParam Long id, @RequestParam float percent) {
//        curl -X POST http://127.0.0.1:8080/api/theater/ticket?price=777
        Discount discount = new Discount(id, percent);
        return this.discountRepository.save(discount);
    }

    @GetMapping("/discounts/{id}")
    Discount getDiscount(@PathVariable Long id) {
//        curl -X GET http://127.0.0.1:8080/api/theater/ticket?price=777
        return this.discountRepository.findDiscountById(id);
    }
}
