package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Discount;
import com.example.demo.ServerSide.repositories.DiscountRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tests")
public class DiscountController {
    private final DiscountRepository discountRepository;


    public DiscountController(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @PostMapping("/addDiscount")
    Discount createDiscount(@RequestParam String id, @RequestParam float percent) {
        Discount discount = new Discount(id, percent);
        return this.discountRepository.save(discount);
    }

    @GetMapping("/getDiscount/{id}")
    Discount getDiscount(@PathVariable String id) {
        return this.discountRepository.findDiscountById(id);
    }
}
