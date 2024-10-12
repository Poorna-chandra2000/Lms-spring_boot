package com.imperion.learn.LMS.PayLoad;

import com.imperion.learn.LMS.Entities.enums.EnrollmentStatus;
import lombok.Data;

@Data
public class EnrolledCourseWithStatusDto {
    private Long courseId;
    private String courseTitle;
    private EnrollmentStatus status;
}
