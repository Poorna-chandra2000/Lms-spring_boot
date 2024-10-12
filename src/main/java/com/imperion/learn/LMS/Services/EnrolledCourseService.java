package com.imperion.learn.LMS.Services;

import com.imperion.learn.LMS.Entities.EnrolledCourse;
import com.imperion.learn.LMS.PayLoad.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrolledCourseService  {
    List<UserDto> getUsersEnrolledInCourse(Long courseId);
}
