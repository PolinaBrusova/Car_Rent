package com.example.demo.ServerSide.controllersDB;

import com.example.demo.ServerSide.models.Client;
import com.example.demo.ServerSide.repositories.ClientRepository;
import com.example.demo.utils.MyLogger;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("api/tests")
public class ClientController {
    private final ClientRepository clientRepository;


    public ClientController(ClientRepository clientRepository) {
        this.clientRepository=clientRepository;
    }

    @PostMapping("/addClient")
    Client createClient(@RequestBody String rawClient) {
        Client client = new Client();
        JSONObject jsonClient = new JSONObject(URLDecoder.decode(rawClient, StandardCharsets.UTF_8));
        client.setFirstName(jsonClient.getString("firstName"));
        client.setLastName(jsonClient.getString("lastName"));
        client.setLiscenceDate(jsonClient.getString("liscenceDate"));
        client.setPassport(jsonClient.getString("passport"));
        client.setPhoneNumber(jsonClient.getString("phoneNumber"));
        MyLogger.inform("Успешно добавлен клиент");
        return this.clientRepository.save(client);
    }

    @GetMapping("/getClient={id}")
    Client getClient(@PathVariable Long id) {
        MyLogger.inform("Найден клиент по id "+id);
        return this.clientRepository.findClientById(id);
    }

    @GetMapping("/AllClients")
    List<Client> getClients() {
        MyLogger.inform("Выведен список всех клиентов");
        return this.clientRepository.findAll();
    }

    @GetMapping("getClient/phone={phone}")
    Client getClientByPhone(@PathVariable String phone){
        MyLogger.inform("Найдена клиент по телефону "+phone);
        return this.clientRepository.findClientByPhoneNumber(URLDecoder.decode(phone, StandardCharsets.UTF_8));
    }

    @Transactional
    @DeleteMapping("/deleteClient={id}")
    public void deleteClient(@PathVariable Long id){
        MyLogger.inform("Удален клиент по id "+id);
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
        MyLogger.inform("Перезаписан клиент по id "+current.getId());
        return this.clientRepository.save(current);
    }
}
