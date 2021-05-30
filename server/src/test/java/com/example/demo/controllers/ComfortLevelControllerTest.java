package com.example.demo.controllers;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ComfortLevelControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public ComfortLevelRepository comfortLevelRepository;

    /***
     * testing GET-request for obtaining one comfort level
     */
    @Test
    void getComfortLevel() {
        try {
            String id = "A";
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/getComfortLevel/"+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(comfortLevelRepository.findComfortLevelById(id).getId(), jsonObject.getString("id"));
                        assertEquals(comfortLevelRepository.findComfortLevelById(id).getDeposit(), jsonObject.getInt("deposit"));
                        assertEquals(comfortLevelRepository.findComfortLevelById(id).getLevel(), jsonObject.getString("level"));
                        assertEquals(comfortLevelRepository.findComfortLevelById(id).getMinExperience(), jsonObject.getInt("minExperience"));
                        assertEquals(comfortLevelRepository.findComfortLevelById(id).getRentPrice(), jsonObject.getInt("rentPrice"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining all rows of comfort levels
     */
    @Test
    void getAllComfortLevels() {
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/AllComfortLevels"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            assertEquals(comfortLevelRepository.findComfortLevelById(jsonObject.getString("id")).getId(), jsonObject.getString("id"));
                            assertEquals(comfortLevelRepository.findComfortLevelById(jsonObject.getString("id")).getDeposit(), jsonObject.getInt("deposit"));
                            assertEquals(comfortLevelRepository.findComfortLevelById(jsonObject.getString("id")).getLevel(), jsonObject.getString("level"));
                            assertEquals(comfortLevelRepository.findComfortLevelById(jsonObject.getString("id")).getMinExperience(), jsonObject.getInt("minExperience"));
                            assertEquals(comfortLevelRepository.findComfortLevelById(jsonObject.getString("id")).getRentPrice(), jsonObject.getInt("rentPrice"));
                        }
                        assertEquals(jsonArray.length(), comfortLevelRepository.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing PUT-request for updating a comfort level
     */
    @Test
    void updateComfortLevel() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id","A");
            jsonObject.put("deposit",2000);
            jsonObject.put("level","Базовый");
            jsonObject.put("minExperience", 1);
            jsonObject.put("rentPrice", 4500);
            this.mvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/tests/updateComfortLevel")
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