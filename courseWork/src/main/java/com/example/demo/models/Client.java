package com.example.demo.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Clients")
public class Client extends AuditModel{
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

    @Column(name = "liscenceDate", nullable = false)
    private LocalDate liscenceDate;

    @Column(name = "Experience", nullable = false)
    private int experience;
    //= Period.between(liscenceDate, LocalDate.now()).getYears();

    public Long getId() {
        return id;
    }

    public int getExperience() {
        return experience;
    }

    public LocalDate getLiscenceDate() {
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLiscenceDate(LocalDate liscenceDate) {
        this.liscenceDate = liscenceDate;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public String toString(){
        return "Client{" +
                "id=" + id + ", First name='" + firstName +
                '\'' + ", Last name='" + lastName +
                '\'' + ", Phone Number='" + phoneNumber +
                '\'' + ", Passport='" + passport +
                '\'' + ", LiscenceDate='" + liscenceDate +
                '\'' + ", Experience in Years='" + experience +
                '\'' + '}';
    }
}