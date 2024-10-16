package com.imperion.learn.LMS.PayLoad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imperion.learn.LMS.Entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrolledDto {

    private Long id;

    @JsonIgnore
    private UserDto user;

    private String username;
    private String email;


    private List<EnrolledCourseDto> enrolledCourses;
}
