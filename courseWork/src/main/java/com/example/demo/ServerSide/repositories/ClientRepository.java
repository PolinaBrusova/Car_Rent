package com.example.demo.ServerSide.repositories;

import com.example.demo.ServerSide.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Service
public interface ClientRepository extends JpaRepository<Client, Integer> {
    public List<Client> findAll();

    public Client findClientById(Long id);

    public  Client findClientByPhoneNumber(String phone);

    public Client removeClientById(Long id);
}
