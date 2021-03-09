package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Rents")
public class Rent extends AuditModel{
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Discount_Id", nullable = false)
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
    private LocalDate totalSumm;

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getTotalSumm() {
        return totalSumm;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTotalSumm(LocalDate totalSumm) {
        this.totalSumm = totalSumm;
    }
}