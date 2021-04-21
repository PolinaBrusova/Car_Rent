package com.example.demo.ServerSide.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Position Entity
 */
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Positions")
public class Position{
    @Id
    @GeneratedValue(generator = "PositionId_generator")
    @SequenceGenerator(
            name = "PositionId_generator",
            sequenceName = "PositionID_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Salary", nullable = false)
    private int salary;

    @Column(name = "NumberOfDaysOff")
    private int numberOfDaysOff;

    @Column(name = "NumberOfWorkingDays")
    private int numberOfWorkingDays;

    /**
     * Empty initializer
     */
    public Position(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public int getNumberOfDaysOff() {
        return numberOfDaysOff;
    }

    public int getNumberOfWorkingDays() {
        return numberOfWorkingDays;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setNumberOfDaysOff(int numberOfDaysOff) {
        this.numberOfDaysOff = numberOfDaysOff;
    }

    public void setNumberOfWorkingDays(int numberOfWorkingDays) {
        this.numberOfWorkingDays = numberOfWorkingDays;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", numberOfDaysOff=" + numberOfDaysOff +
                ", numberOfWorkingDays=" + numberOfWorkingDays +
                '}';
    }
}
