package com.example.demo.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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

    public Position(String name, int salary, Integer[] args){
        this.name = name;
        this.salary = salary;
        if (args.length==2){
            this.numberOfDaysOff = args[0];
            this.numberOfWorkingDays = args[1];
        }else{
            this.numberOfDaysOff = 0;
            this.numberOfWorkingDays = 0;
        }
    }

    public Position(String name, Integer salary, Integer numberOfDaysOff, Integer numberOfWorkDays){}

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
