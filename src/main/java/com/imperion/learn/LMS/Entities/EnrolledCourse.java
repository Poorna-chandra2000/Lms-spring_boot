package com.imperion.learn.LMS.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imperion.learn.LMS.Entities.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//ensure a user can enroll one course once
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"enrolled_id","course_id"})
})
public class EnrolledCourse extends AuditableEntity{
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

   //enrolledcourses can be in enrolled section of logged in user
   @ManyToOne
    @JoinColumn(name = "enrolled_id",referencedColumnName = "id",nullable = false)
    private Enrolled enrolled;

   @ManyToOne
   @JoinColumn(name = "course_id",referencedColumnName = "id",nullable = false)
    private Course course;

   @Enumerated(EnumType.STRING)
    private EnrollmentStatus status=EnrollmentStatus.PENDING;

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
