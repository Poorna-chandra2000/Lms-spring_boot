package com.imperion.learn.LMS.PayLoad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imperion.learn.LMS.Entities.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
//ensure a user can enroll one course once
public class EnrolledCourseDto {

    Long id;

    @JsonIgnore
    private EnrolledDto enrolled;



    private CourseDto course;

    private EnrollmentStatus status;


}
