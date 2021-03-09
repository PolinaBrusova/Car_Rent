package com.example.demo.models;

import javax.persistence.*;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Discounts")
public class Discount extends AuditModel{
    @Id
    private Long id;

    @Column(name = "Percent", nullable = false)
    private float percent;

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
    public Date getCreatedDate() {
        return super.getCreatedDate();
    }

    @Override
    public Date getUpdateDate() {
        return super.getUpdateDate();
    }

    @Override
    public String toString(){
        return "Discount{" +
                "id=" + id + ", percent='" + percent +
                '\'' + '}';
    }
}
