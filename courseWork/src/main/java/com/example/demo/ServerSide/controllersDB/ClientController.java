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
        return this.clientRepository.save(client);
    }

    @GetMapping("/getClient={id}")
    Client getClient(@PathVariable Long id) {
        return this.clientRepository.findClientById(id);
    }

    @GetMapping("/AllClients")
    List<Client> getClients() {
        return this.clientRepository.findAll();
    }

    @GetMapping("getClient/phone={phone}")
    Client getClientByPhone(@PathVariable String phone){
        return this.clientRepository.findClientByPhoneNumber(phone);
    }

    @Transactional
    @DeleteMapping("/deleteClient={id}")
    public void deleteClient(@PathVariable Long id){
        this.clientRepository.deleteClientById(id);
    }

    @Transactional
    @PutMapping("/updateClient")
    public Client updateClient(@RequestBody Client newClient){
        Client current = this.clientRepository.findClientById(newClient.getId());
        current.setId(newClient.getId());
        current.setFirstName(newClient.getFirstName());
        current.setLastName(newClient.getLastName());
        current.setPassport(newClient.getPassport());
        current.setLiscenceDate(newClient.getLiscenceDate());
        return this.clientRepository.save(current);
    }
}
