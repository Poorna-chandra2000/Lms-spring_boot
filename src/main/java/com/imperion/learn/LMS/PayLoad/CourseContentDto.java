package com.imperion.learn.LMS.PayLoad;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseContentDto {

    private Long id;

    private String content;

    @JsonIgnore
    private CourseDto course;

}
