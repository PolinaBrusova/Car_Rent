package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.*;
import com.example.demo.ServerSide.repositories.*;
import com.example.demo.utils.DateUtil;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    @PostMapping("/addRent")
    Rent createRent(@RequestBody String lineRent) {
        Rent rent = new Rent();
        JSONObject rawRent = new JSONObject(URLDecoder.decode(lineRent, StandardCharsets.UTF_8));
        rent.setStartDate(DateUtil.parse(rawRent.getString("startDate")));
        rent.setEndDate(DateUtil.parse(rawRent.getString("endDate")));
        rent.setTotalSumm(rawRent.getFloat("totalSum"));
        rent.setEmployee(this.employeeRepository.findEmployeeById(rawRent.getJSONObject("employee").getLong("id")));
        rent.setClient(this.clientRepository.findClientById(rawRent.getJSONObject("client").getLong("id")));
        rent.setCar(this.carRepository.findCarById(rawRent.getJSONObject("car").getLong("id")));
        rent.setDiscount(this.discountRepository.findDiscountById(rawRent.getJSONObject("discount").getString("id")));
        return this.rentRepository.save(rent);
    }

    @GetMapping("/getRent={id}")
    Rent getRent(@PathVariable Long id) {
        return this.rentRepository.findRentById(id);
    }

    @GetMapping("/ApproproateCars/gearBox={gearbox}&doors={doors}&seats={seats}&comfortLevel={comfortLevel}&startDate={start}&exp={exp}")
    List<Car> getAppropriate(@PathVariable String gearbox, @PathVariable String doors, @PathVariable String seats, @PathVariable String comfortLevel, @PathVariable String start, @PathVariable int exp){
        LocalDate startTime = DateUtil.parse(start);
        List<Car> finalList = new ArrayList<>();
        List<Rent> rents = this.rentRepository.findAll();
        List<Car> allCars = this.carRepository.findAll();
        rents.sort((u1, u2) -> u2.getEndDate().compareTo(u1.getEndDate()));
        for(Car car: allCars){
            int errors = 0;
            List<Rent> thisCarRents = this.rentRepository.findRentsByCarId(car.getId());
            if (thisCarRents.size()==0){
                if (car.isAvailable()){
                    if (car.getComfortLevel().getMinExperience()<=exp){
                        if (!comfortLevel.matches("-")){
                            if (!car.getComfortLevel().getId().matches(comfortLevel)){
                                errors+=1;
                            }
                        }
                        if (!doors.matches("-")){
                            if (!String.valueOf(car.getDoorNumber()).matches(doors)){
                                errors+=1;
                            }
                        }
                        if (!seats.matches("-")){
                            if (!String.valueOf(car.getSeats()).matches(seats)){
                                errors+=1;
                            }
                        }
                        if (!gearbox.matches("-")){
                            if (!car.getGearbox().matches(gearbox)){
                                errors+=1;
                            }
                        }
                        if (errors == 0){
                            finalList.add(car);
                        }
                    }
                }
            }else{
                thisCarRents.sort((u1, u2) -> u2.getEndDate().compareTo(u1.getEndDate()));
                if (startTime != null && startTime.isAfter(thisCarRents.get(0).getEndDate()) && car.isAvailable()){
                    if (car.getComfortLevel().getMinExperience()<=exp){
                        if (!comfortLevel.matches("-")){
                            if (!car.getComfortLevel().getId().matches(comfortLevel)){
                                errors+=1;
                            }
                        }
                        if (!doors.matches("-")){
                            if (!String.valueOf(car.getDoorNumber()).matches(doors)){
                                errors+=1;
                            }
                        }
                        if (!seats.matches("-")){
                            if (!String.valueOf(car.getSeats()).matches(seats)){
                                errors+=1;
                            }
                        }
                        if (!gearbox.matches("-")){
                            if (!car.getGearbox().matches(gearbox)){
                                errors+=1;
                            }
                        }
                        if (errors ==0){
                            finalList.add(car);
                        }
                    }
                }
            }
        }
        return finalList;
    }

    @GetMapping("Client={id}/rents")
    int getNumberOfRents(@PathVariable Long id){
        return this.rentRepository.findRentsByClientId(id).size();
    }

    @GetMapping("Car={id}/rents")
    boolean getFreeCar(@PathVariable Long id){
        List<Rent> rents = this.rentRepository.findRentsByCarId(id);
        if (rents.size()==0){
            return true;
        }else{
            rents.sort((u1, u2) -> u2.getEndDate().compareTo(u1.getEndDate()));
            return rents.get(0).getEndDate().isBefore(LocalDate.now());
        }
    }

    @GetMapping("CarByRentId={id}")
    Car getCarbyRentID(@PathVariable Long id){
        return this.carRepository.findCarById(this.rentRepository.findRentById(id).getCar().getId());
    }

    @GetMapping("ClientByRentId={id}")
    Client getClientbyRentID(@PathVariable Long id){
        return this.clientRepository.findClientById(this.rentRepository.findRentById(id).getClient().getId());
    }

    @GetMapping("DiscountByRentId={id}")
    Discount getDiscountbyRentID(@PathVariable Long id){
        return this.discountRepository.findDiscountById(this.rentRepository.findRentById(id).getDiscount().getId());
    }

    @GetMapping("Client={id}/isRenting")
    boolean getFreeClient(@PathVariable Long id){
        List<Rent> rents = this.rentRepository.findRentsByClientId(id);
        if (rents.size()==0){
            return true;
        }else{
            rents.sort((u1, u2) -> u2.getEndDate().compareTo(u1.getEndDate()));
            return rents.get(0).getEndDate().isBefore(LocalDate.now());
        }
    }

    @GetMapping("AllRents")
    List<Rent> getAllRents(){
        return  this.rentRepository.findAll();
    }

    @GetMapping("discount_for_client={id}")
    Discount getDiscountForClient(@PathVariable Long id){
        int rents = getNumberOfRents(id);
        Client client = this.clientRepository.findClientById(id);
        try{
            if (Period.between(Objects.requireNonNull(DateUtil.parse(client.getLiscenceDate())), LocalDate.now()).getYears()>=7) {
                return this.discountRepository.findDiscountById("PROFI");
            }else{
                if (rents == 0){
                    return this.discountRepository.findDiscountById("STARTER");
                }else{
                    if (rents%10==0){
                        return this.discountRepository.findDiscountById("GIFT");
                    }else{
                        if(rents>10){
                            return this.discountRepository.findDiscountById("FAVOURITE");
                        }else{
                            return this.discountRepository.findDiscountById("NO DISCOUNT");
                        }
                    }
                }
            }
        }catch (NullPointerException e){
            if (rents == 0){
                return this.discountRepository.findDiscountById("STARTER");
            }else{
                if (rents%10==0){
                    return this.discountRepository.findDiscountById("GIFT");
                }else{
                    if(rents>10){
                        return this.discountRepository.findDiscountById("FAVOURITE");
                    }else{
                        return this.discountRepository.findDiscountById("NO DISCOUNT");
                    }
                }
            }
        }
    }

    @GetMapping("/statistics")
    HashMap<String, Float> getStatistics(){
        List<Rent> rents = this.rentRepository.findAll();
        HashMap<String, Float> data = new HashMap<>();
        for (Rent rent: rents){
            if(rent.getStartDate().getYear()==2021){
                if (data.containsKey(rent.getStartDate().getMonth().name())){
                    data.replace(rent.getStartDate().getMonth().name(), data.get(rent.getStartDate().getMonth().name())+rent.getTotalSumm());
                }else{
                    data.put(rent.getStartDate().getMonth().name(), rent.getTotalSumm());
                }
            }
        }
        return data;
    }
}
