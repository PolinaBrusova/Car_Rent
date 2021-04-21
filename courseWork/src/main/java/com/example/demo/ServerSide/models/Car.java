package com.example.demo.ServerSide.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;

/**
 * Car entity
 */
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "Cars")
public class Car{
    @Id
    @GeneratedValue(generator = "CarId_generator")
    @SequenceGenerator(
            name = "CarId_generator",
            sequenceName = "CarID_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(name = "Brand", nullable = false)
    private String brand;

    @Column(name = "Carcase", nullable = false)
    private String carcase;

    @Column(name = "Gearbox", nullable = false)
    private String gearbox;

    @Column(name = "DoorNumber", nullable = false)
    private int doorNumber;

    @Column(name = "Seats", nullable = false)
    private int seats;

    @Column(name = "ReleaseYear", nullable = false)
    private int releaseYear;

    @Column(name = "Color", nullable = false)
    private String color;

    @Column(name = "Available", nullable = false)
    private boolean available;

    /**
     * Initializes Car and assigns all parameters
     * @param brand String value of the brand
     * @param carcase String value of the carcase
     * @param gearbox String value of the gearbox
     * @param doorNumber Integer value of the number of doors
     * @param seats Integer value of the number of seats
     * @param releaseYear Integer value of the year (4 characters)
     * @param color String value of the color
     */
    public Car(String brand, String carcase, String gearbox, Integer doorNumber, Integer seats,
               Integer releaseYear, String color) {
        this.brand=brand;
        this.carcase=carcase;
        this.gearbox=gearbox;
        this.doorNumber=doorNumber;
        this.seats=seats;
        this.releaseYear=releaseYear;
        this.color=color;
        this.available = true;
    }

    /**
     * Empty initializer
     */
    public Car(){}

    public Long getId(){
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getGearbox() {
        return gearbox;
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    public int getSeats() {
        return seats;
    }

    public String getCarcase() {
        return carcase;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getColor() {
        return color;
    }

    public boolean isAvailable() {
        return available;
    }

    public SimpleStringProperty getBrandProperty(){return new SimpleStringProperty(brand);}

    public SimpleStringProperty getGearProperty(){return new SimpleStringProperty(gearbox);}

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCarcase(String carcase) {
        this.carcase = carcase;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ComfortLevel_Id", nullable = false)
    @JsonIgnore
    private ComfortLevel comfortLevel;

    public ComfortLevel getComfortLevel() {
        return comfortLevel;
    }

    public void setComfortLevel(ComfortLevel comfortLevel) {
        this.comfortLevel = comfortLevel;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", carcase='" + carcase + '\'' +
                ", gearbox='" + gearbox + '\'' +
                ", doorNumber=" + doorNumber +
                ", seats=" + seats +
                ", releaseYear=" + releaseYear +
                ", color='" + color + '\'' +
                ", available=" + available +
                ", comfortLevel=" + comfortLevel +
                '}';
    }
}