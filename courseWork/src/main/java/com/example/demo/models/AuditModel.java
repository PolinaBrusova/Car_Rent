package com.example.demo.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value={"createdAt", "updatedAt"},
        allowGetters = true
)
public class AuditModel implements Serializable {

    @Column(name = "created_at", nullable = false, updatable = false, insertable=false)
    @CreatedDate

    private Date createdDate;

    @Column(name = "created_at", nullable = false, insertable=false, updatable=false)
    @LastModifiedDate

    private Date updateDate;

    public Date getCreatedDate(){
        return createdDate;
    }

    public Date getUpdateDate(){
        return updateDate;
    }







}