package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Cars")
public class Car extends AuditModel{
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
    public String toString(){
        return "CarDao{" +
                "id=" + id + ", Brand='" + brand +
                '\'' + ", Carcase='" + carcase +
                '\'' + ", Gearbox='" + gearbox +
                '\'' + ", Number of doors='" + doorNumber +
                '\'' + ", Number of seats='" + seats +
                '\'' + ", Release Year='" + releaseYear +
                '\'' + ", Color='" + color +
                '\'' + '}';
    }
}