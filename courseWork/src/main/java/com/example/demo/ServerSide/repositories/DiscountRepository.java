package com.example.demo.ServerSide.repositories;

import com.example.demo.ServerSide.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Service
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    public List<Discount> findAll();

    public  Discount findDiscountById(String id);
}
