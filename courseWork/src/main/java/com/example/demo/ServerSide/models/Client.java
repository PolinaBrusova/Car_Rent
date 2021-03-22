package com.example.demo.ServerSide.models;

import com.example.demo.utils.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Clients")
public class Client{
    @Id
    @GeneratedValue(generator = "ClientId_generator")
    @SequenceGenerator(
            name = "ClientId_generator",
            sequenceName = "ClientID_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "LastName", nullable = false)
    private String lastName;

    @Column(name = "PhoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "Passport", nullable = false)
    private String passport;

    @Column(name = "liscenceDate")
    private String liscenceDate;

    @Column(name = "Experience", nullable = false)
    private int experience;

    public Client(String firstName, String lastName, String phoneNumber, String passport, String liscenceDate){
        this.firstName=firstName;
        this.lastName=lastName;
        this.phoneNumber=phoneNumber;
        this.passport=passport;
        this.liscenceDate = liscenceDate;
        this.experience = Period.between(DateUtil.parse(liscenceDate), LocalDate.now()).getYears();
    }

    public Client(){}

    public Long getId() {
        return id;
    }

    public int getExperience() {
        return experience;
    }

    public String getLiscenceDate() {
        return liscenceDate;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassport() {
        return passport;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLiscenceDate(String liscenceDate) {
        this.liscenceDate = liscenceDate;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringProperty getFirstNameProperty() { return new SimpleStringProperty(firstName); }
    public StringProperty getLastNameProperty() { return new SimpleStringProperty(lastName);}

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", passport='" + passport + '\'' +
                ", liscenceDate=" + liscenceDate +
                ", experience=" + experience +
                '}';
    }
}