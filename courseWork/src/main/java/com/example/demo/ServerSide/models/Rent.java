package com.example.demo.ServerSide.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

/**
 * Rent Entity
 */
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Rents")
public class Rent{
    @Id
    @GeneratedValue(generator = "RentId_generator")
    @SequenceGenerator(
            name = "RentId_generator",
            sequenceName = "RentID_sequence",
            initialValue = 1000
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Car_Id", nullable = false)
    @JsonIgnore
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Client_Id", nullable = false)
    @JsonIgnore
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "Discount_Id", nullable = true)
    @JsonIgnore
    private Discount discount;

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Employee_Id", nullable = false)
    @JsonIgnore
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Column(name = "Start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "End_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_summ", nullable = false)
    private float totalSumm;

    /**
     * Initializes Rent and assigns all parameters
     * @param startDate LocalDate value of the start day of the renting
     * @param endDate LocalDate value of the end day of the renting
     */
    public Rent(LocalDate startDate, LocalDate endDate){
        this.startDate=startDate;
        this.endDate=endDate;
        this.totalSumm = Period.between(endDate, startDate).getDays()*
                car.getComfortLevel().getRentPrice()*(1-discount.getPercent())+car.getComfortLevel().getDeposit();
    }

    /**
     * Empty initializer
     */
    public Rent(){}

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public float getTotalSumm() {
        return totalSumm;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTotalSumm(float totalSumm) {
        this.totalSumm = totalSumm;
    }

    public SimpleStringProperty getBrandProperty(){
        return new SimpleStringProperty(this.car.getBrand());
    }

    public SimpleStringProperty getLastNameProperty(){
        return new SimpleStringProperty(this.client.getFirstName());
    }

    @Override
    public String toString() {
        return "Rent{" +
                "id=" + id +
                ", car=" + car +
                ", client=" + client +
                ", discount=" + discount +
                ", employee=" + employee +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalSumm=" + totalSumm +
                '}';
    }
}