package com.example.demo.ServerSide.controllersDB;

import com.example.demo.models.*;
import com.example.demo.ServerSide.repositories.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;

@RestController
@RequestMapping("api/tests")
public class RentController {
    private final RentRepository rentRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;
    private final DiscountRepository discountRepository;


    public RentController(EmployeeRepository employeeRepository, ClientRepository clientRepository,
                              CarRepository carRepository, DiscountRepository discountRepository,
                          RentRepository rentRepository) {
        this.employeeRepository=employeeRepository;
        this.clientRepository=clientRepository;
        this.carRepository=carRepository;
        this.discountRepository=discountRepository;
        this.rentRepository=rentRepository;
    }

    @PostMapping("/rents")
    Rent createRent(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate,
                    @RequestParam Employee employee, @RequestParam Client client,
                    @RequestParam Discount discount, @RequestParam Car car) {
//        curl -X POST http://127.0.0.1:8080/api/theater/ticket?price=777
        Rent rent = new Rent(startDate, endDate);
        rent.setCar(carRepository.findCarById(car.getId()));
        rent.setClient(clientRepository.findClientById(client.getId()));
        rent.setDiscount(discountRepository.findDiscountById(discount.getId()));
        rent.setEmployee(employeeRepository.findEmployeeById(employee.getId()));
        rent.setTotalSumm(Period.between(endDate, startDate).getDays()*
                car.getComfortLevel().getRentPrice()*(1-discount.getPercent())+car.getComfortLevel().getDeposit());
        return this.rentRepository.save(rent);
    }

    @GetMapping("/rents/{id}")
    Rent getRent(@PathVariable Long id) {
//        curl -X GET http://127.0.0.1:8080/api/theater/ticket?price=777
        return this.rentRepository.findRentById(id);
    }
}
