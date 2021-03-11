package com.example.demo.repositories;

import com.example.demo.models.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Service
public interface RentRepository extends JpaRepository<Rent, Integer> {
    //Rent findRentByCarId(Long id);
    public List<Rent> findAll();
}
