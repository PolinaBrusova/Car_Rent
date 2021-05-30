package com.example.demo.controllers;

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
class PositionControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public PositionRepository positionRepository;

    /***
     * testing GET-request for obtaining one position
     */
    @Test
    void getPosition() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/getPosition="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(positionRepository.findPositionById(id).getId(), jsonObject.getLong("id"));
                        assertEquals(positionRepository.findPositionById(id).getName(), jsonObject.getString("name"));
                        assertEquals(positionRepository.findPositionById(id).getSalary(), jsonObject.getInt("salary"));
                        assertEquals(positionRepository.findPositionById(id).getNumberOfDaysOff(), jsonObject.getInt("numberOfDaysOff"));
                        assertEquals(positionRepository.findPositionById(id).getNumberOfWorkingDays(), jsonObject.getInt("numberOfWorkingDays"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}