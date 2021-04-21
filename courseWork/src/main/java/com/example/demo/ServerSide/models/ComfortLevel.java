package com.example.demo.ServerSide.models;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Comfort Level entity
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "ComfortLevels")
public class ComfortLevel{
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

    /**
     * Initializes Comfort Level and assigns all parameters
     * @param id String value of the id
     * @param level String value of the full name of the level
     * @param deposit Integer value of the deposit sum
     * @param rentPrice Integer value of the rent price per day
     * @param minExperience Integer value of the minimum experience required
     */
    public ComfortLevel(String id, String level, Long deposit, long rentPrice, int minExperience){
        this.id = id;
        this.level = level;
        this.deposit = deposit;
        this.rentPrice = rentPrice;
        this.minExperience = minExperience;
    }

    /**
     * Empty initializer
     */
    public ComfortLevel(){}

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

    public SimpleStringProperty getLetterProperty(){return new SimpleStringProperty(id);}

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
    public String toString() {
        return "ComfortLevel{" +
                "id='" + id + '\'' +
                ", level='" + level + '\'' +
                ", deposit=" + deposit +
                ", rentPrice=" + rentPrice +
                ", minExperience=" + minExperience +
                '}';
    }
}