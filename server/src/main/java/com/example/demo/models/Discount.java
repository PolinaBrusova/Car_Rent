package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Discount entity
 */
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Discounts")
public class Discount{
    @Id
    private String id;

    @Column(name = "Percent", nullable = false)
    private float percent;

    /**
     * Empty initializer
     */
    public Discount(){}

    /**
     * return the id value
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     * return the percent value
     * @return Float percent
     */
    public float getPercent() {
        return percent;
    }

    /**
     * Sets the value for id field
     * @param id String value for id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the value for percent field
     * @param percent Float value for percent
     */
    public void setPercent(float percent) {
        this.percent = percent;
    }

    /**
     * Converts information to String object
     * @return String representation of the Discount object
     */
    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", percent=" + percent +
                '}';
    }
}
