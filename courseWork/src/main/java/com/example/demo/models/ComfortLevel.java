package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "ComfortLevels")
public class ComfortLevel extends AuditModel{
    @Id
    private String id; //Уровень комфорта от низшего(D) До высшего (S) (DCBAS)

    @Column(name = "Level", nullable = false)
    private String level;//расшифровка уровня типо эконом или комфорт

    @Column(name = "Deposit_sum", nullable = false)
    private Long deposit;

    @Column(name = "Rent_price", nullable = false)
    private Long rentPrice;

    @Column(name = "MinExperience", nullable = false)
    private int minExperience;

    public String getId(){
        return id;
    }

    public String getLevel() {
        return level;
    }

    public Long getDeposit() {
        return deposit;
    }

    public Long getRentPrice() {
        return rentPrice;
    }

    public int getMinExperience() {
        return minExperience;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public void setRentPrice(Long rentPrice) {
        this.rentPrice = rentPrice;
    }

    public void setMinExperience(int minExperience) {
        this.minExperience = minExperience;
    }

    @Override
    public String toString(){
        return "ComfortLevel{" +
                "id=" + id + ", Level='" + level +
                '\'' + ", Deposit sum='" + deposit +
                '\'' + ", RentDao price='" + rentPrice +
                '\'' + ", Minimum experience='" + minExperience +
                '\'' + '}';
    }
}