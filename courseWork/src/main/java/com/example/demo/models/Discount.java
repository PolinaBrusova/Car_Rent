package com.example.demo.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Discounts")
public class Discount{
    @Id
    private Long id;

    @Column(name = "Percent", nullable = false)
    private float percent;

    public Discount(Long id, float percent){
        this.id=id;
        this.percent=percent;
    }

    public Discount(){}

    public Long getId() {
        return id;
    }

    public float getPercent() {
        return percent;
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
