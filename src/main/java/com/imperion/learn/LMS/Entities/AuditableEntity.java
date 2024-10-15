package com.imperion.learn.LMS.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//now inorder for auditing
//we need to create auditable class with MappedSuperClass annot
// and entity listener anot with entitylistner.class and extend it here
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)//audititng entity listener
@Getter
@Setter
public class AuditableEntity {
//created date created by,lastModifiedDate last modifiedBy
    //for this create a controller for this only for admins if necessry
    //auditable reader
    //now we need to create a bean in congig class along with modelmapper
    //and Audit aware class
    @CreatedDate
        //if you dont wanna change local date time just mak eit nullable updatable null
        @Column(nullable = true, updatable = false)
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;

    @CreatedBy
    String createdBy;

    @LastModifiedBy
    String updatedBy;

}
