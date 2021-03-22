package com.example.demo.ServerSide.models;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "Departments")
public class Department{
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

    @Column(name = "Additional", nullable = false)
    private String additional;

    public Department(String adress, String head, String phone, String email, String additional){
        this.adress=adress;
        this.head=head;
        this.phone=phone;
        this.email=email;
        this.additional=additional;
    }

    public Department(){}

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
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", adress='" + adress + '\'' +
                ", head='" + head + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", additional='" + additional + '\'' +
                '}';
    }
}
