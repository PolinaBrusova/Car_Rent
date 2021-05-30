package com.example.demo.controllers;

import com.example.demo.repositories.ClientRepository;
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
class ClientControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public ClientRepository clientRepository;

    /***
     * testing POST-request for creating new client
     */
    @Test
    void createClient() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstName","Test");
            jsonObject.put("lastName","Test");
            jsonObject.put("liscenceDate", "2009-12-13");
            jsonObject.put("passport", "1111111111");
            jsonObject.put("phoneNumber", "+76545434343");
            this.mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/tests/addClient")
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
     * testing GET-request for obtaining one client
     */
    @Test
    void getClient() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/getClient="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(clientRepository.findClientById(id).getId(), jsonObject.getLong("id"));
                        assertEquals(clientRepository.findClientById(id).getFirstName(), jsonObject.getString("firstName"));
                        assertEquals(clientRepository.findClientById(id).getLastName(), jsonObject.getString("lastName"));
                        assertEquals(clientRepository.findClientById(id).getLiscenceDate(), jsonObject.getString("liscenceDate"));
                        assertEquals(clientRepository.findClientById(id).getPassport(), jsonObject.getString("passport"));
                        assertEquals(clientRepository.findClientById(id).getPhoneNumber(), jsonObject.getString("phoneNumber"));
                        assertEquals(clientRepository.findClientById(id).getExperience(), jsonObject.getInt("experience"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining all rows of clients
     */
    @Test
    void getClients() {
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/AllClients"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            assertEquals(clientRepository.findClientById(jsonObject.getLong("id")).getId(), jsonObject.getLong("id"));
                            assertEquals(clientRepository.findClientById(jsonObject.getLong("id")).getFirstName(), jsonObject.getString("firstName"));
                            assertEquals(clientRepository.findClientById(jsonObject.getLong("id")).getLastName(), jsonObject.getString("lastName"));
                            assertEquals(clientRepository.findClientById(jsonObject.getLong("id")).getLiscenceDate(), jsonObject.getString("liscenceDate"));
                            assertEquals(clientRepository.findClientById(jsonObject.getLong("id")).getPassport(), jsonObject.getString("passport"));
                            assertEquals(clientRepository.findClientById(jsonObject.getLong("id")).getPhoneNumber(), jsonObject.getString("phoneNumber"));
                            assertEquals(clientRepository.findClientById(jsonObject.getLong("id")).getExperience(), jsonObject.getInt("experience"));
                        }
                        assertEquals(jsonArray.length(), clientRepository.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing GET-request for obtaining a client with specified phone number
     */
    @Test
    void getClientByPhone() {
        try{
            String phone = "+79878767654";
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/api/tests/getClient/phone="+phone))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(clientRepository.findClientByPhoneNumber(phone).getId(), jsonObject.getLong("id"));
                        assertEquals(clientRepository.findClientByPhoneNumber(phone).getFirstName(), jsonObject.getString("firstName"));
                        assertEquals(clientRepository.findClientByPhoneNumber(phone).getLastName(), jsonObject.getString("lastName"));
                        assertEquals(clientRepository.findClientByPhoneNumber(phone).getLiscenceDate(), jsonObject.getString("liscenceDate"));
                        assertEquals(clientRepository.findClientByPhoneNumber(phone).getPassport(), jsonObject.getString("passport"));
                        assertEquals(clientRepository.findClientByPhoneNumber(phone).getPhoneNumber(), jsonObject.getString("phoneNumber"));
                        assertEquals(clientRepository.findClientByPhoneNumber(phone).getExperience(), jsonObject.getInt("experience"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * testing DELETE-request for deleting one client
     */
    @Test
    void deleteClient() {
        try{
            long id =1;
            this.mvc.perform(MockMvcRequestBuilders.delete("http://localhost:9090/api/tests/deleteClient="+id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***
     * testing PUT-request for updating a client
     */
    @Test
    void updateClient() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",2);
            jsonObject.put("firstName","Test");
            jsonObject.put("lastName","Test");
            jsonObject.put("liscenceDate", "2009-12-13");
            jsonObject.put("passport", "1111111111");
            jsonObject.put("phoneNumber", "+76545434343");
            this.mvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/tests/updateClient")
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