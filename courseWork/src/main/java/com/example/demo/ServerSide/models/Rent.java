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

    /**
     * return the car value
     * @return Car car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Sets the value for car field
     * @param car Car value for car
     */
    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Client_Id", nullable = false)
    @JsonIgnore
    private Client client;

    /**
     * return the client value
     * @return Client client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets the value for client field
     * @param client Client value for client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "Discount_Id", nullable = true)
    @JsonIgnore
    private Discount discount;

    /**
     * return the discount value
     * @return Discount discount
     */
    public Discount getDiscount() {
        return discount;
    }

    /**
     * Sets the value for discount field
     * @param discount Discount value for discount
     */
    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Employee_Id", nullable = false)
    @JsonIgnore
    private Employee employee;

    /**
     * return the employee value
     * @return Employee employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the value for employee field
     * @param employee Employee value for employee
     */
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
                car.getComfortLevel().getRentPrice()*(1-discount.getPercent())+
                car.getComfortLevel().getDeposit();
    }

    /**
     * Empty initializer
     */
    public Rent(){}

    /**
     * return the id value
     * @return Long id
     */
    public Long getId() {
        return id;
    }

    /**
     * return the startDate value
     * @return LocalDate startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * return the endDate value
     * @return LocalDate endDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * return the totalSumm value
     * @return Float totalSumm
     */
    public float getTotalSumm() {
        return totalSumm;
    }

    /**
     * Sets the value for id field
     * @param id Long value for id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the value for startDate field
     * @param startDate LocalDate value for startDate
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Sets the value for endDate field
     * @param endDate LocalDate value for endDate
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the value for totalSumm field
     * @param totalSumm Float value for totalSumm
     */
    public void setTotalSumm(float totalSumm) {
        this.totalSumm = totalSumm;
    }

    /**
     * return the property for brand
     * @return created SimpleStringProperty
     */
    public SimpleStringProperty getBrandProperty(){
        return new SimpleStringProperty(this.car.getBrand());
    }

    /**
     * return the property for firstName
     * @return created SimpleStringProperty
     */
    public SimpleStringProperty getLastNameProperty(){
        return new SimpleStringProperty(this.client.getFirstName());
    }

    /**
     * Converts information to String object
     * @return String representation of the Rent object
     */
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