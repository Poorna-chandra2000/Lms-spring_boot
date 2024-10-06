package com.imperion.learn.LMS.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String coursesCategory;

    @OneToMany(mappedBy = "category",
    cascade = {CascadeType.PERSIST},orphanRemoval = false)
    List<Course> courses;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(Id, category.Id) && Objects.equals(coursesCategory, category.coursesCategory) && Objects.equals(courses, category.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, coursesCategory, courses);
    }
}
