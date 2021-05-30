package com.example.demo.controllers;

import com.example.demo.repositories.CarRepository;
import com.example.demo.repositories.ComfortLevelRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public CarRepository carRepository;
    @Autowired
    public ComfortLevelRepository comfortLevelRepository;

    /***
     * Testing POST-request for creating a car
     */
    @Test
    void createCar() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("brand","Mazda CX5");
            jsonObject.put("carcase","ХэтчБэк");
            jsonObject.put("gearbox", "Auto");
            jsonObject.put("color", "Зеленый");
            jsonObject.put("doorNumber", 4);
            jsonObject.put("seats", 5);
            jsonObject.put("releaseYear", 2019);
            jsonObject.put("available", true);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("id", "A");
            jsonObject.put("comfortLevel", jsonObject1);
            this.mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/tests/addCar")
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
     * testing GET-request for obtaining one car
     */
    @Test
    void getCar() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/getCar="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(carRepository.findCarById(id).getId(), jsonObject.getLong("id"));
                        assertEquals(carRepository.findCarById(id).getBrand(), jsonObject.getString("brand"));
                        assertEquals(carRepository.findCarById(id).getCarcase(), jsonObject.getString("carcase"));
                        assertEquals(carRepository.findCarById(id).getGearbox(), jsonObject.getString("gearbox"));
                        assertEquals(carRepository.findCarById(id).getDoorNumber(), jsonObject.getInt("doorNumber"));
                        assertEquals(carRepository.findCarById(id).getSeats(), jsonObject.getInt("seats"));
                        assertEquals(carRepository.findCarById(id).getReleaseYear(), jsonObject.getInt("releaseYear"));
                        assertEquals(carRepository.findCarById(id).getColor(), jsonObject.getString("color"));
                        assertEquals(carRepository.findCarById(id).isAvailable(), jsonObject.getBoolean("available"));
                        assertEquals(carRepository.findCarById(id).getComfortLevel().getId(),
                                jsonObject.getJSONObject("comfortLevel").getString("id"));

                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Testing GET-request for getting all rows of cars
     */
    @Test
    void getAllCars() {
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/AllCars"))
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
                        assertEquals(jsonArray.length(), carRepository.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining one comfort level of a specified car's id
     */
    @Test
    void getLevelByCarId() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/LevelByCarId="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(comfortLevelRepository.findComfortLevelById(jsonObject.getString("id")).getId(), jsonObject.getString("id"));
                        assertEquals(comfortLevelRepository.findComfortLevelById(jsonObject.getString("id")).getLevel(), jsonObject.getString("level"));
                        assertEquals(comfortLevelRepository.findComfortLevelById(jsonObject.getString("id")).getDeposit(), jsonObject.getInt("deposit"));
                        assertEquals(comfortLevelRepository.findComfortLevelById(jsonObject.getString("id")).getRentPrice(), jsonObject.getLong("rentPrice"));
                        assertEquals(comfortLevelRepository.findComfortLevelById(jsonObject.getString("id")).getMinExperience(), jsonObject.getInt("minExperience"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * Testing DELETE-request for deleting a car
     */
    @Test
    void deleteCar() {
        try{
            long id =1;
            this.mvc.perform(MockMvcRequestBuilders.delete("http://localhost:9090/api/tests/deleteCar="+id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * Testing PUT-request for updating a car
     */
    @Test
    void updateCar() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id","1");
            jsonObject.put("brand","Mazda CX5");
            jsonObject.put("carcase","ХэтчБэк");
            jsonObject.put("gearbox", "Auto");
            jsonObject.put("color", "Зеленый");
            jsonObject.put("doorNumber", 4);
            jsonObject.put("seats", 5);
            jsonObject.put("releaseYear", 2019);
            jsonObject.put("available", true);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("id", "A");
            jsonObject.put("comfortLevel", jsonObject1);
            this.mvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/tests/updateCar")
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
}