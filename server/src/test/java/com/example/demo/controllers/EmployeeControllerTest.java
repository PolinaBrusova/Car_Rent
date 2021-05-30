package com.example.demo.controllers;

import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.PositionRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public PositionRepository positionRepository;

    /***
     * testing GET-request for obtaining one employee
     */
    @Test
    void getEmployee() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/getEmployee="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(employeeRepository.findEmployeeById(id).getId(), jsonObject.getLong("id"));
                        assertEquals(employeeRepository.findEmployeeById(id).getAdress(), jsonObject.getString("adress"));
                        assertEquals(employeeRepository.findEmployeeById(id).getEmail(), jsonObject.getString("email"));
                        assertEquals(employeeRepository.findEmployeeById(id).getFirstName(), jsonObject.getString("firstName"));
                        assertEquals(employeeRepository.findEmployeeById(id).getLastName(), jsonObject.getString("lastName"));
                        assertEquals(employeeRepository.findEmployeeById(id).getPassport(), jsonObject.getString("passport"));
                        assertEquals(employeeRepository.findEmployeeById(id).getPhone(), jsonObject.getString("phone"));
                        assertEquals(employeeRepository.findEmployeeById(id).getPosition().getId(),
                                jsonObject.getJSONObject("position").getInt("id"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining a position of am employee with specified id
     */
    @Test
    void getPositionById() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/positionById="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(positionRepository.findPositionById(jsonObject.getLong("id")).getId(), jsonObject.getLong("id"));
                        assertEquals(positionRepository.findPositionById(jsonObject.getLong("id")).getName(), jsonObject.getString("name"));
                        assertEquals(positionRepository.findPositionById(jsonObject.getLong("id")).getSalary(), jsonObject.getInt("salary"));
                        assertEquals(positionRepository.findPositionById(jsonObject.getLong("id")).getNumberOfDaysOff(), jsonObject.getInt("numberOfDaysOff"));
                        assertEquals(positionRepository.findPositionById(jsonObject.getLong("id")).getNumberOfWorkingDays(), jsonObject.getInt("numberOfWorkingDays"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}