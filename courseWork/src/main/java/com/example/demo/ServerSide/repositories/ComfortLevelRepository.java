package com.example.demo.ServerSide.repositories;

import com.example.demo.models.ComfortLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComfortLevelRepository extends JpaRepository<ComfortLevel, Integer> {
    public ComfortLevel findComfortLevelById(String id);
    public  List<ComfortLevel> findAll();
}
