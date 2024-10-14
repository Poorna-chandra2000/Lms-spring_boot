package com.imperion.learn.LMS.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseContent extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private User author;

    @ManyToOne
    @JoinColumn(name = "course_id",nullable = false)
    @JsonIgnore
    private Course course;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseContent that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, course);
    }

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
