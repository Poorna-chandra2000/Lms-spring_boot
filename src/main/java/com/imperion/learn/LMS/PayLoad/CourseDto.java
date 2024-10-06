package com.imperion.learn.LMS.PayLoad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imperion.learn.LMS.Entities.Category;
import com.imperion.learn.LMS.Entities.EnrolledCourse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {


    private Long id;

    private String coursename;

    private String description;

    private String image;

    @JsonIgnore
    Category category;


    //if course is deleted its associated content get deleted
    //but if content is delete the course i.e parent doesnt get deleted
   @JsonIgnore
    private List<CourseContentDto> contentList;

}
