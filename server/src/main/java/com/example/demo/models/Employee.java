package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Employee Entity
 */
@Entity
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

    /**
     * Empty initializer
     */
    public Employee(){}

    /**
     * return the id value
     * @return Long id
     */
    public Long getId() {
        return id;
    }

    /**
     * return the firstName value
     * @return String firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * return the lastName value
     * @return String lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * return the phone value
     * @return String phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * return the email value
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * return the passport value
     * @return String passport
     */
    public String getPassport() {
        return passport;
    }

    /**
     * return the adress value
     * @return String adress
     */
    public String getAdress() {
        return adress;
    }

    /**
     * Sets the value for id field
     * @param id Long value for id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the value for id field
     * @param firstName String value for id
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the value for lastName field
     * @param lastName String value for lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the value for phone field
     * @param phone String value for phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Sets the value for email field
     * @param email String value for email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the value for passport field
     * @param passport String value for passport
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }

    /**
     * Sets the value for adress field
     * @param adress String value for adress
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Position_Id", nullable = false)
    @JsonIgnore
    private Position position;

    /**
     * return the id value
     * @return Long id
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the value for position field
     * @param position Position value for position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Converts information to String object
     * @return String representation of the Employee object
     */
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
                ", position=" + position +
                '}';
    }
}
