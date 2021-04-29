package sample.models;

import sample.utils.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Client entity
 */
public class Client{
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String passport;
    private String liscenceDate;
    private int experience;

    /**
     * Initializes Client and assigns all parameters
     * @param firstName String value of the first name
     * @param lastName String value of the last name
     * @param phoneNumber String value of phone number
     * @param passport String value of passport code
     * @param liscenceDate String value of the date of obtaining a licence
     * Experience parameter is calculated from the licence date
     */
    public Client(String firstName, String lastName, String phoneNumber, String passport,
                  String liscenceDate){
        this.firstName=firstName;
        this.lastName=lastName;
        this.phoneNumber=phoneNumber;
        this.passport=passport;
        this.liscenceDate = liscenceDate;
        try{
            this.experience = Period.between(Objects.requireNonNull(DateUtil.parse(liscenceDate)),
                    LocalDate.now()).getYears();
        }catch (NullPointerException e){
            this.experience = 0;
        }
    }

    /**
     * Empty initializer
     */
    public Client(){}

    /**
     * return the id value
     * @return Long id
     */
    public Long getId() {
        return id;
    }

    /**
     * return the experience value
     * @return Integer experience
     */
    public int getExperience() {
        return experience;
    }

    /**
     * return the licence date value
     * @return String liscenceDate
     */
    public String getLiscenceDate() {
        return liscenceDate;
    }

    /**
     * return the last name value
     * @return String lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * return the passport value
     * @return String passport
     */
    public String getPassport() {
        return passport;
    }

    /**
     * return the phone number value
     * @return String phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * return the first name value
     * @return String firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value for id field
     * @param id Long value for id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the value for firstName field
     * @param firstName String value for firstName
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
     * Sets the value for liscenceDate field
     * @param liscenceDate String value for liscenceDate
     */
    public void setLiscenceDate(String liscenceDate) {
        this.liscenceDate = liscenceDate;
        try{
            this.experience = Period.between(Objects.requireNonNull(DateUtil.parse(liscenceDate)), LocalDate.now()).getYears();
        }catch (NullPointerException e){
            this.experience = 0;
        }
    }

    /**
     * Sets the value for passport field
     * @param passport String value for passport
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }

    /**
     * Sets the value for phoneNumber field
     * @param phoneNumber String value for phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * return the property for firstName
     * @return created SimpleStringProperty
     */
    public StringProperty getFirstNameProperty() { return new SimpleStringProperty(firstName); }

    /**
     * return the property for lastName
     * @return created SimpleStringProperty
     */
    public StringProperty getLastNameProperty() { return new SimpleStringProperty(lastName);}

    /**
     * Converts information to String object
     * @return String representation of the Client object
     */
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