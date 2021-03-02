package com.example.demo.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Person {
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty phone;  //Разобраться какой проперти нужен чтобы влезал весь номер
    private StringProperty passport;  //Рвзобраться в проперти что бы влезал весь паспорт
    private ObjectProperty<LocalDate> liscence;
    public Person(){
        this(null,null);
    }

    public Person(String firstName, String lastName){
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        this.phone = new SimpleStringProperty("+79878767656");
        this.passport = new SimpleStringProperty("5678 765445");
        this.liscence = new SimpleObjectProperty<LocalDate>(LocalDate.of(1995,12,12));
    }
    public String getFirstName() {
        return firstName.get();
    }
    public String getLastName() {
        return lastName.get();
    }
    public String getPhone() { return phone.get(); }

    public String getPassport() { return passport.get(); }
    public LocalDate getLiscence() { return liscence.get(); }

    public StringProperty getFirstNameProperty() {
        return firstName;
    }
    public StringProperty getLastNameProperty() { return lastName;}
    public StringProperty getPhoneProperty() { return phone;}
    public StringProperty getPassportProperty() {return passport;}
    public ObjectProperty<LocalDate> getLiscenceProperty() { return liscence; }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public void setLiscence(LocalDate liscence) {
        this.liscence.set(liscence);
    }
    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    public void setPassport(String passport) {
        this.passport.set(passport);
    }
}
