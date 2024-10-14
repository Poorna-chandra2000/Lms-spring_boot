package com.imperion.learn.LMS.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrolled extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //one user can have only one enrolled  section just like single cart per user
    @OneToOne
    @JoinColumn(name = "user_id",
    referencedColumnName = "id",nullable = false)
    private User user;

    private String username;
    private String email;

    //this enrolled section can have many enrolled course
    @OneToMany(mappedBy = "enrolled",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<EnrolledCourse> enrolledCourses=new ArrayList<>();

    //now inorder for auditing
    //we need to create auditable class with MappedSuperClass annot and entity listener and extend it here

    //also add persist keep it untouched your wish
    //enable jpa auditing anot in congig class
    @PrePersist
    void beforeSave(){

    }

    @PreRemove
    void beforeDelete(){

    }

    @PreUpdate
    void beforeUpdate(){

    }
}
