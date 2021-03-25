package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Client;
import com.example.demo.ServerSide.repositories.ClientRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tests")
public class ClientController {
    private final ClientRepository clientRepository;


    public ClientController(ClientRepository clientRepository) {
        this.clientRepository=clientRepository;
    }

    @PostMapping("/addClient")
    Client createClient(@RequestBody Client client) {
        System.out.println(client);
        return this.clientRepository.save(client);
    }

    @GetMapping("/getClient={id}")
    Client getClient(@PathVariable Long id) {
        return this.clientRepository.findClientById(id);
    }

    @GetMapping("/AllClients")
    List<Client> getClients() {
        List<Client> clients = this.clientRepository.findAll();
        return this.clientRepository.findAll();
    }

    @GetMapping("getClient/phone={phone}")
    Client getClientByPhone(@PathVariable String phone){
        return this.clientRepository.findClientByPhoneNumber(phone);
    }

    @DeleteMapping("/deleteClient={id}")
    Client deleteClientById(@PathVariable Long id){
        return this.clientRepository.removeClientById(id);
    }
}
