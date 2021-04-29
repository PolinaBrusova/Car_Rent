package com.example.demo.ServerSide.models;

import javax.persistence.*;

/**
 * EmpLogPas Entity
 */
@Entity
@Table(name = "EmpLogPas")
public class EmpLogPas{
    @Id
    private Long id;

    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Empty initializer
     */
    public EmpLogPas(){}

    /**
     * return the id value
     * @return Long id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value for id field
     * @param id Long value for id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * return the password value
     * @return String password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value for id password
     * @param password String value for password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Converts information to String object
     * @return String representation of the EmpLogPas object
     */
    @Override
    public String toString() {
        return "EmpLogPas{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
