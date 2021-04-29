package sample.models;

import javafx.beans.property.SimpleStringProperty;

/**
 * Comfort Level entity
 */
public class ComfortLevel{
    private String id;
    private String level;
    private Long deposit;
    private Long rentPrice;
    private int minExperience;

    /**
     * Initializes Comfort Level and assigns all parameters
     * @param id String value of the id
     * @param level String value of the full name of the level
     * @param deposit Integer value of the deposit sum
     * @param rentPrice Integer value of the rent price per day
     * @param minExperience Integer value of the minimum experience required
     */
    public ComfortLevel(String id, String level, Long deposit, long rentPrice, int minExperience){
        this.id = id;
        this.level = level;
        this.deposit = deposit;
        this.rentPrice = rentPrice;
        this.minExperience = minExperience;
    }

    /**
     * Empty initializer
     */
    public ComfortLevel(){}

    /**
     * return the id value
     * @return String id
     */
    public String getId(){
        return id;
    }

    /**
     * return the level value
     * @return String level
     */
    public String getLevel() {
        return level;
    }

    /**
     * return the deposit value
     * @return Long deposit
     */
    public Long getDeposit() {
        return deposit;
    }

    /**
     * return the rentPrice value
     * @return Long rentPrice
     */
    public Long getRentPrice() {
        return rentPrice;
    }

    /**
     * return the minExperience value
     * @return Integer minExperience
     */
    public int getMinExperience() {
        return minExperience;
    }

    /**
     * return the property for id
     * @return created SimpleStringProperty
     */
    public SimpleStringProperty getLetterProperty(){return new SimpleStringProperty(id);}

    /**
     * Sets the value for id field
     * @param id String value for id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the value for level field
     * @param level String value for level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Sets the value for deposit field
     * @param deposit Long value for deposit
     */
    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    /**
     * Sets the value for rentPrice field
     * @param rentPrice Long value for rentPrice
     */
    public void setRentPrice(Long rentPrice) {
        this.rentPrice = rentPrice;
    }

    /**
     * Sets the value for minExperience field
     * @param minExperience Integer value for minExperience
     */
    public void setMinExperience(int minExperience) {
        this.minExperience = minExperience;
    }

    /**
     * Converts information to String object
     * @return String representation of the ComfortLevel object
     */
    @Override
    public String toString() {
        return "ComfortLevel{" +
                "id='" + id + '\'' +
                ", level='" + level + '\'' +
                ", deposit=" + deposit +
                ", rentPrice=" + rentPrice +
                ", minExperience=" + minExperience +
                '}';
    }
}