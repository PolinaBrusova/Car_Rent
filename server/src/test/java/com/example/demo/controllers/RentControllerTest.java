package com.example.demo.controllers;

import com.example.demo.models.Car;
import com.example.demo.models.Client;
import com.example.demo.models.Discount;
import com.example.demo.models.Rent;
import com.example.demo.repositories.*;
import com.example.demo.utils.DateUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RentControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public RentRepository rentRepository;
    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    public CarRepository carRepository;
    @Autowired
    public DiscountRepository discountRepository;

    /***
     * testing POST-request for creating a rent
     */
    @Test
    void createRent() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("startDate","2020-12-12");
            jsonObject.put("endDate","2020-12-15");
            jsonObject.put("totalSumm", 10000);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("id", 1);
            jsonObject.put("car", jsonObject1);
            JSONObject jsonObject2 = new JSONObject();
            jsonObject1.put("id", 1);
            jsonObject.put("client", jsonObject2);
            JSONObject jsonObject3 = new JSONObject();
            jsonObject1.put("id", 1);
            jsonObject.put("employee", jsonObject3);
            JSONObject jsonObject4 = new JSONObject();
            jsonObject1.put("id", "PROFI");
            jsonObject.put("discount", jsonObject4);
            this.mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/tests/addRent")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonObject.toString())
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining one rent
     */
    @Test
    void getRent() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/getRent="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(rentRepository.findRentById(id).getId(), jsonObject.getLong("id"));
                        assertEquals(rentRepository.findRentById(id).getStartDate(), LocalDate.parse(jsonObject.getString("startDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        assertEquals(rentRepository.findRentById(id).getEndDate(), LocalDate.parse(jsonObject.getString("endDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        assertEquals(rentRepository.findRentById(id).getTotalSumm(), jsonObject.getDouble("totalSumm"));
                        assertEquals(rentRepository.findRentById(id).getCar().getId(), jsonObject.getJSONObject("car").getLong("id"));
                        assertEquals(rentRepository.findRentById(id).getClient().getId(), jsonObject.getJSONObject("client").getLong("id"));
                        assertEquals(rentRepository.findRentById(id).getDiscount().getId(), jsonObject.getJSONObject("discount").getString("id"));
                        assertEquals(rentRepository.findRentById(id).getEmployee().getId(), jsonObject.getJSONObject("employee").getLong("id"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining cars that suit the specifications
     */
    @Test
    void getAppropriate() {
        try{
            String gear = "Auto";
            String doors = "4";
            String seats = "5";
            String comfort = "A";
            String start = "2021-05-30";
            int exp = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/ApproproateCars/gearBox="+gear+"&doors="+doors+"&seats="+seats+"&comfortLevel="+comfort+"&startDate="+start+"&exp="+exp))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            assertEquals(carRepository.findCarById(jsonObject.getLong("id")).getId(), jsonObject.getLong("id"));
                            assertEquals(carRepository.findCarById(jsonObject.getLong("id")).getBrand(), jsonObject.getString("brand"));
                            assertEquals(carRepository.findCarById(jsonObject.getLong("id")).getCarcase(), jsonObject.getString("carcase"));
                            assertEquals(carRepository.findCarById(jsonObject.getLong("id")).getGearbox(), jsonObject.getString("gearbox"));
                            assertEquals(carRepository.findCarById(jsonObject.getLong("id")).getDoorNumber(), jsonObject.getInt("doorNumber"));
                            assertEquals(carRepository.findCarById(jsonObject.getLong("id")).getSeats(), jsonObject.getInt("seats"));
                            assertEquals(carRepository.findCarById(jsonObject.getLong("id")).getReleaseYear(), jsonObject.getInt("releaseYear"));
                            assertEquals(carRepository.findCarById(jsonObject.getLong("id")).getColor(), jsonObject.getString("color"));
                            assertEquals(carRepository.findCarById(jsonObject.getLong("id")).isAvailable(), jsonObject.getBoolean("available"));
                            assertEquals(carRepository.findCarById(jsonObject.getLong("id")).getComfortLevel().getId(),
                                    jsonObject.getJSONObject("comfortLevel").getString("id"));
                        }
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
                                        if (!comfort.matches("-")){
                                            if (!car.getComfortLevel().getId().matches(comfort)){
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
                                        if (!gear.matches("-")){
                                            if (!car.getGearbox().matches(gear)){
                                                errors+=1;
                                            }
                                        }
                                        if (errors == 0){
                                            finalList.add(car);
                                        }
                                    }
                                }
                            }else{
                                LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                thisCarRents.sort((u1, u2) -> u2.getEndDate().compareTo(u1.getEndDate()));
                                if (startDate != null && startDate.isAfter(thisCarRents.get(0).getEndDate()) && car.isAvailable()){
                                    if (car.getComfortLevel().getMinExperience()<=exp){
                                        if (!comfort.matches("-")){
                                            if (!car.getComfortLevel().getId().matches(comfort)){
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
                                        if (!gear.matches("-")){
                                            if (!car.getGearbox().matches(gear)){
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
                        assertEquals(jsonArray.length(), finalList.size());
                    })
                    .andReturn();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining number of rents one client has
     */
    @Test
    void getNumberOfRents() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/Client="+id+"/rents"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        assertEquals(String.valueOf(rentRepository.findRentsByClientId(id)), body);
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining if car is allowed to be rented today
     */
    @Test
    void getFreeCar() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/Car="+id+"/rents"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        List<Rent> rents = this.rentRepository.findRentsByCarId(id);
                        boolean expected;
                        if (rents.size()==0){
                            expected = true;
                        }else{
                            rents.sort((u1, u2) -> u2.getEndDate().compareTo(u1.getEndDate()));
                            expected = rents.get(0).getEndDate().isBefore(LocalDate.now());
                        }
                        assertEquals(String.valueOf(expected), body);
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining car of the rent
     */
    @Test
    void getCarbyRentID() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/CarByRentId="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(carRepository.findCarById(rentRepository.findRentById(id).getCar().getId()).getId(), jsonObject.getLong("id"));
                        assertEquals(carRepository.findCarById(rentRepository.findRentById(id).getCar().getId()).getBrand(), jsonObject.getString("brand"));
                        assertEquals(carRepository.findCarById(rentRepository.findRentById(id).getCar().getId()).getCarcase(), jsonObject.getString("carcase"));
                        assertEquals(carRepository.findCarById(rentRepository.findRentById(id).getCar().getId()).getGearbox(), jsonObject.getString("gearbox"));
                        assertEquals(carRepository.findCarById(rentRepository.findRentById(id).getCar().getId()).getDoorNumber(), jsonObject.getInt("doorNumber"));
                        assertEquals(carRepository.findCarById(rentRepository.findRentById(id).getCar().getId()).getSeats(), jsonObject.getInt("seats"));
                        assertEquals(carRepository.findCarById(rentRepository.findRentById(id).getCar().getId()).getReleaseYear(), jsonObject.getInt("releaseYear"));
                        assertEquals(carRepository.findCarById(rentRepository.findRentById(id).getCar().getId()).getColor(), jsonObject.getString("color"));
                        assertEquals(carRepository.findCarById(rentRepository.findRentById(id).getCar().getId()).isAvailable(), jsonObject.getBoolean("available"));
                        assertEquals(carRepository.findCarById(rentRepository.findRentById(id).getCar().getId()).getComfortLevel().getId(),
                                jsonObject.getJSONObject("comfortLevel").getString("id"));

                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining client of the rent
     */
    @Test
    void getClientbyRentID() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/ClientByRentId="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(clientRepository.findClientById(rentRepository.findRentById(id).getClient().getId()).getId(), jsonObject.getLong("id"));
                        assertEquals(clientRepository.findClientById(rentRepository.findRentById(id).getClient().getId()).getFirstName(), jsonObject.getString("firstName"));
                        assertEquals(clientRepository.findClientById(rentRepository.findRentById(id).getClient().getId()).getLastName(), jsonObject.getString("lastName"));
                        assertEquals(clientRepository.findClientById(rentRepository.findRentById(id).getClient().getId()).getLiscenceDate(), jsonObject.getString("liscenceDate"));
                        assertEquals(clientRepository.findClientById(rentRepository.findRentById(id).getClient().getId()).getPassport(), jsonObject.getString("passport"));
                        assertEquals(clientRepository.findClientById(rentRepository.findRentById(id).getClient().getId()).getPhoneNumber(), jsonObject.getString("phoneNumber"));
                        assertEquals(clientRepository.findClientById(rentRepository.findRentById(id).getClient().getId()).getExperience(), jsonObject.getInt("experience"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining discount of the rent
     */
    @Test
    void getDiscountbyRentID() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/DiscountByRentId="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(discountRepository.findDiscountById(rentRepository.findRentById(id).getDiscount().getId()).getId(), jsonObject.getString("id"));
                        assertEquals(discountRepository.findDiscountById(rentRepository.findRentById(id).getDiscount().getId()).getPercent(), jsonObject.getDouble("percent"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining if client can start new rent today
     */
    @Test
    void getFreeClient() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/Client="+id+"/isRenting"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        List<Rent> rents = this.rentRepository.findRentsByClientId(id);
                        boolean expected;
                        if (rents.size()==0){
                            expected = true;
                        }else{
                            rents.sort((u1, u2) -> u2.getEndDate().compareTo(u1.getEndDate()));
                            expected = rents.get(0).getEndDate().isBefore(LocalDate.now());
                        }
                        assertEquals(String.valueOf(expected), body);
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining all rows of rents
     */
    @Test
    void getAllRents() {
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/AllRents"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            assertEquals(rentRepository.findRentById(jsonObject.getLong("id")).getId(), jsonObject.getLong("id"));
                            assertEquals(rentRepository.findRentById(jsonObject.getLong("id")).getStartDate(), LocalDate.parse(jsonObject.getString("startDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            assertEquals(rentRepository.findRentById(jsonObject.getLong("id")).getEndDate(), LocalDate.parse(jsonObject.getString("endDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            assertEquals(rentRepository.findRentById(jsonObject.getLong("id")).getTotalSumm(), jsonObject.getDouble("totalSumm"));
                            assertEquals(rentRepository.findRentById(jsonObject.getLong("id")).getCar().getId(), jsonObject.getJSONObject("car").getLong("id"));
                            assertEquals(rentRepository.findRentById(jsonObject.getLong("id")).getClient().getId(), jsonObject.getJSONObject("client").getLong("id"));
                            assertEquals(rentRepository.findRentById(jsonObject.getLong("id")).getDiscount().getId(), jsonObject.getJSONObject("discount").getString("id"));
                            assertEquals(rentRepository.findRentById(jsonObject.getLong("id")).getEmployee().getId(), jsonObject.getJSONObject("employee").getLong("id"));
                        }
                        assertEquals(jsonArray.length(), rentRepository.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining a suitable discount for a specified client
     */
    @Test
    void getDiscountForClient() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/discount_for_client="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        int rents = this.rentRepository.findRentsByClientId(id).size();
                        Client client = this.clientRepository.findClientById(id);
                        Discount expected;
                        try{
                            if (Period.between(Objects.requireNonNull(DateUtil.parse(client.getLiscenceDate())), LocalDate.now()).getYears()>=7) {
                                expected = this.discountRepository.findDiscountById("PROFI");
                            }else{
                                if (rents == 0){
                                    expected = this.discountRepository.findDiscountById("STARTER");
                                }else{
                                    if (rents%10==0){
                                        expected = this.discountRepository.findDiscountById("GIFT");
                                    }else{
                                        if(rents>10){
                                            expected = this.discountRepository.findDiscountById("FAVOURITE");
                                        }else{
                                            expected = this.discountRepository.findDiscountById("NO DISCOUNT");
                                        }
                                    }
                                }
                            }
                        }catch (NullPointerException e){
                            if (rents == 0){
                                expected = this.discountRepository.findDiscountById("STARTER");
                            }else{
                                if (rents%10==0){
                                    expected = this.discountRepository.findDiscountById("GIFT");
                                }else{
                                    if(rents>10){
                                        expected = this.discountRepository.findDiscountById("FAVOURITE");
                                    }else{
                                        expected = this.discountRepository.findDiscountById("NO DISCOUNT");
                                    }
                                }
                            }
                        }
                        assertEquals(expected.getId(), jsonObject.getString("id"));
                        assertEquals(expected.getPercent(), jsonObject.getDouble("percent"));

                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}