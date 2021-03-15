package com.example.demo.controllersDB;

import com.example.demo.models.Client;
import com.example.demo.repositories.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/tests")
public class ClientController {
    private final ClientRepository clientRepository;


    public ClientController(ClientRepository clientRepository) {
        this.clientRepository=clientRepository;
    }

    @PostMapping("/clients")
    Client createClient(@RequestParam String firstName, @RequestParam String lastName,
                        @RequestParam String phone, @RequestParam String passport,
                        @RequestParam LocalDate liscence) {
//        curl -X POST http://127.0.0.1:8080/api/theater/ticket?price=777
        Client client = new Client(firstName, lastName, phone, passport, liscence);
        return this.clientRepository.save(client);
    }

    @GetMapping("/clients/{id}")
    Client getClient(@PathVariable Long id) {
//        curl -X GET http://127.0.0.1:8080/api/theater/ticket?price=777
        return this.clientRepository.findClientById(id);
    }
}
