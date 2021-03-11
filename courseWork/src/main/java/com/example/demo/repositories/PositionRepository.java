package com.example.demo.repositories;

import com.example.demo.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Service
public interface PositionRepository extends JpaRepository<Position, Integer> {
    public List<Position> findAll();
}
