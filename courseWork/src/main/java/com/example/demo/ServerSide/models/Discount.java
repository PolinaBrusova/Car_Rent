package com.example.demo.ServerSide.models;

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

    public String getId() {
        return id;
    }

    public float getPercent() {
        return percent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id=" + id +
                ", percent=" + percent +
                '}';
    }
}
