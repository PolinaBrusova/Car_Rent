package sample.models;

import javafx.beans.property.SimpleStringProperty;

/**
 * Car entity
 */
public class Car{
    private Long id;
    private String brand;
    private String carcase;
    private String gearbox;
    private int doorNumber;
    private int seats;
    private int releaseYear;
    private String color;
    private boolean available;
    private ComfortLevel comfortLevel;

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

    /**
     * return the id value
     * @return Long id
     */
    public Long getId(){
        return id;
    }

    /**
     * return the brand value
     * @return String brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * return the gearbox value
     * @return String id
     */
    public String getGearbox() {
        return gearbox;
    }

    /**
     * return the number of doors value
     * @return Integer doorNumber
     */
    public int getDoorNumber() {
        return doorNumber;
    }

    /**
     * return the number of seats value
     * @return Integer seats
     */
    public int getSeats() {
        return seats;
    }

    /**
     * return the carcase value
     * @return String carcase
     */
    public String getCarcase() {
        return carcase;
    }

    /**
     * return the release year value
     * @return Integer releaseYear
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * return the color value
     * @return String color
     */
    public String getColor() {
        return color;
    }

    /**
     * return the available value
     * @return boolean isAvailable
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * return the property for brand
     * @return created SimpleStringProperty
     */
    public SimpleStringProperty getBrandProperty(){return new SimpleStringProperty(brand);}

    /**
     * return the property for gearbox
     * @return created SimpleStringProperty
     */
    public SimpleStringProperty getGearProperty(){return new SimpleStringProperty(gearbox);}


    /**
     * Sets the value for id field
     * @param id Long value for id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the value for brand field
     * @param brand String value for brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Sets the value for carcase field
     * @param carcase String value for carcase
     */
    public void setCarcase(String carcase) {
        this.carcase = carcase;
    }

    /**
     * Sets the value for color field
     * @param color String value for color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Sets the value for gearbox field
     * @param gearbox String value for gearbox
     */
    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    /**
     * Sets the value for doorNumber field
     * @param doorNumber Integer value for doorNumber
     */
    public void setDoorNumber(int doorNumber) {
        this.doorNumber = doorNumber;
    }

    /**
     * Sets the value for releaseYear field
     * @param releaseYear Integer value for releaseYear
     */
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * Sets the value for seats field
     * @param seats Integer value for seats
     */
    public void setSeats(int seats) {
        this.seats = seats;
    }

    /**
     * Sets the value for available field
     * @param available Boolean value for available
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * return the comfort level assigned to the car value
     * @return ComfortLevel comfortlevel
     */
    public ComfortLevel getComfortLevel() {
        return comfortLevel;
    }

    /**
     * Sets the value for comfortLevel field
     * @param comfortLevel ComfortLevel value for comfortLevel
     */
    public void setComfortLevel(ComfortLevel comfortLevel) {
        this.comfortLevel = comfortLevel;
    }

    /**
     * Converts information to String object
     * @return String representation of the Car object
     */
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