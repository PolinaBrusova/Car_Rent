package com.example.demo.controllers;

import com.example.demo.repositories.DiscountRepository;
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
class DiscountControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public DiscountRepository discountRepository;

    /***
     * testing GET-request for obtaining one discount
     */
    @Test
    void getDiscount() {
        try {
            String id = "PROFI";
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/getDiscount/"+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(discountRepository.findDiscountById(id).getId(), jsonObject.getString("id"));
                        assertEquals(discountRepository.findDiscountById(id).getPercent(), jsonObject.getDouble("percent"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}