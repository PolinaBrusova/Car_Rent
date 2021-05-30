package com.example.demo.controllers;

import com.example.demo.repositories.EmpLogPasReposiroty;
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
class LogPasControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public EmpLogPasReposiroty empLogPasReposiroty;

    /***
     * testing GET-request for obtaining validation on login and password
     */
    @Test
    void canLogin() {
        int login = 1;
        String password = "test";
        try{
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/LogPas_Id="+login+"_password="+password))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        assertEquals(String.valueOf(empLogPasReposiroty.findEmpLogPasById(login).getPassword().equals(password.hashCode())), body);
                    })
                    .andReturn();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}