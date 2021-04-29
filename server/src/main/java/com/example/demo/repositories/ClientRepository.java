package com.example.demo.repositories;

import com.example.demo.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface that extends JpaRepository of Client, Integer
 */
@Repository
@Service
public interface ClientRepository extends JpaRepository<Client, Integer> {
    /**
     * Finds all rows from the table
     * @return List of Client with all found rows
     */
    List<Client> findAll();

    /**
     * Finds a Client by its id from the table
     * @param id Long value of the id
     * @return Client object found (or null - if not found)
     */
    Client findClientById(Long id);

    /**
     * Finds a Client by its phone number from the table
     * @param phone String value of the phone number
     * @return Client object found (or null - if not found)
     */
    Client findClientByPhoneNumber(String phone);

    /**
     * Deletes a Client by its id from the table
     * @param id Long value of the id
     */
    void deleteClientById(Long id);
}
