package com.imperion.learn.LMS.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imperion.learn.LMS.PayLoad.EnrolledCourseDto;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String coursename;

    private String description;

    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;


    //if course is deleted its associated content get deleted
    //but if content is delete the course i.e parent doesnt get deleted
    @OneToMany(mappedBy = "course",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private List<CourseContent> contentList;


    //if cascade all it will automatically delete the course from enrollment too everywhere
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<EnrolledCourse> enrolledCourse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return Objects.equals(id, course.id) && Objects.equals(coursename, course.coursename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coursename);
    }
}
