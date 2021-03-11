package com.example.demo.repositories;

import com.example.demo.models.ComfortLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Service
public interface ComfortLevelRepository extends JpaRepository<ComfortLevel, Integer> {
    public  List<ComfortLevel> findAll();
}
