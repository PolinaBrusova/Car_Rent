package com.example.demo.controllers;

import com.example.demo.models.Client;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.utils.MyLogger;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * Rest Controller for the database
 */
@RestController
@RequestMapping("api/tests")
public class ClientController {
    private final ClientRepository clientRepository;

    /**
     * Initialises the controller and assigns the private variables
     * @param clientRepository ClientRepository - JPA Repository child
     */
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository=clientRepository;
    }

    /**
     * Saves a car object to the database via the ClientRepository - JPA Repository child
     * The line must specify all field that are mentioned as not null in the Client class except the id
     * @param rawClient String that contains JSON with the class fields
     * @return Client object that has been saved
     */
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

    /**
     * Finds a Client by it's id in the database via the ClientRepository - JPA Repository child
     * This method always returns immediately, whether or not the Client exists in the database.
     * @param id Long value of the Client id in the database
     * @return Client object
     */
    @GetMapping("/getClient={id}")
    Client getClient(@PathVariable Long id) {
        MyLogger.inform("Найден клиент по id "+id);
        return this.clientRepository.findClientById(id);
    }

    /**
     * Finds all Clients that exist in the database via the ClientRepository - JPA Repository child
     * This method always returns immediately, whether or not the database has any rows.
     * @return List of Client objects
     */
    @GetMapping("/AllClients")
    List<Client> getClients() {
        MyLogger.inform("Выведен список всех клиентов");
        return this.clientRepository.findAll();
    }

    /**
     * Finds a Client by it's phone number in the database via the ClientRepository - JPA Repository child
     * This method always returns immediately, whether or not the database has any rows.
     * @param phone String value of the phone number
     * @return Client object
     */
    @GetMapping("getClient/phone={phone}")
    Client getClientByPhone(@PathVariable String phone){
        MyLogger.inform("Найдена клиент по телефону "+phone);
        return this.clientRepository.findClientByPhoneNumber(URLDecoder.decode(phone, StandardCharsets.UTF_8));
    }

    /**
     * Deletes the Client by it's id from the database via the ClientRepository - JPA Repository child
     * @param id Long value of the Client id in the database
     */
    @Transactional
    @DeleteMapping("/deleteClient={id}")
    public void deleteClient(@PathVariable Long id){
        MyLogger.inform("Удален клиент по id "+id);
        this.clientRepository.deleteClientById(id);
    }

    /**
     * Updates the fields of the Client via the ClientRepository - JPA Repository child
     * The line must specify all field that are mentioned as not null in the Client class AND the id
     * @param newClient Client object
     * @return updated Client object
     */
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
