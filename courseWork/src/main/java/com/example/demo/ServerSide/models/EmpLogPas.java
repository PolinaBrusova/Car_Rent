package com.example.demo.ServerSide.models;

import javax.persistence.*;

@Entity
@Table(name = "EmpLogPas")
public class EmpLogPas{
    @Id
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    public EmpLogPas(Long id, String password){
        this.id=id;
        this.password=password;
    }

    public EmpLogPas(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "EmpLogPas{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
