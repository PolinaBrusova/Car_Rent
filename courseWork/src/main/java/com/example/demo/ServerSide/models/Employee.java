package com.example.demo.ServerSide.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Employees")
public class Employee{
    @Id
    @GeneratedValue(generator = "EmployeeId_generator")
    @SequenceGenerator(
            name = "EmployeeId_generator",
            sequenceName = "EmployeeID_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "LastName", nullable = false)
    private String lastName;

    @Column(name = "Phone", nullable = false)
    private String phone;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Passport", nullable = false)
    private String passport;

    @Column(name = "Adress", nullable = false)
    private String adress;

    @Column(name = "sales", nullable = false)
    private int sales;

    public Employee(String firstName, String lastName, String phone, String email, String passport, String adress, int sales){
        this.firstName=firstName;
        this.lastName=lastName;
        this.phone=phone;
        this.email=email;
        this.passport=passport;
        this.adress=adress;
        this.sales = sales;
    }

    public Employee(){}

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassport() {
        return passport;
    }

    public String getAdress() {
        return adress;
    }

    public int getSales() {
        return sales;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Department_Id", nullable = false)
    @JsonIgnore
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Position_Id", nullable = false)
    @JsonIgnore
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", passport='" + passport + '\'' +
                ", adress='" + adress + '\'' +
                ", department=" + department +
                ", position=" + position +
                '}';
    }
}
