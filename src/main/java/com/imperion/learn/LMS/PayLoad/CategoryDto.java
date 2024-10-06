package com.imperion.learn.LMS.PayLoad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imperion.learn.LMS.Entities.Course;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    private String coursesCategory;

    @JsonIgnore
    List<Course> courses;
}
