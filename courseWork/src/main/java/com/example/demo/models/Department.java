package com.example.demo.models;

import javax.persistence.*;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Departments")
public class Department extends AuditModel{
    @Id
    @GeneratedValue(generator = "DepartmentId_generator")
    @SequenceGenerator(
            name = "DepartmentId_generator",
            sequenceName = "DepartmentID_sequence",
            initialValue = 1000
    )
    private Long id;

    @Column(name = "Adress", nullable = false)
    private String adress;

    @Column(name = "Head", nullable = false)
    private String head;

    @Column(name = "Phone", nullable = false)
    private String phone;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Additional")
    private String additional;

    public Long getId() {
        return id;
    }

    public String getAdress() {
        return adress;
    }

    public String getHead() {
        return head;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
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
        return "Department{" +
                "id=" + id + ", Adress='" + adress +
                '\'' + ", Head='" + head +
                '\'' + ", Phone'" + phone +
                '\'' + ", Email='" + email +
                '\'' + ", Additional='" + additional +
                '\'' + '}';
    }
}
