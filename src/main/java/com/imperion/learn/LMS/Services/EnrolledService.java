package com.imperion.learn.LMS.Services;

import com.imperion.learn.LMS.Entities.Enrolled;
import com.imperion.learn.LMS.Entities.EnrolledCourse;
import com.imperion.learn.LMS.Entities.User;
import com.imperion.learn.LMS.PayLoad.CourseContentDto;
import com.imperion.learn.LMS.PayLoad.EnrolledCourseDto;
import com.imperion.learn.LMS.PayLoad.EnrolledCourseWithStatusDto;
import com.imperion.learn.LMS.PayLoad.EnrolledDto;

import java.util.List;

public interface EnrolledService {

    Enrolled getOrCreateEnrolledForCurrentUser();


    List<CourseContentDto> getCourseContent(Long courseId);

    EnrolledDto getEnrolled(User user);

    EnrolledCourseDto enrollUserInCourse(Long courseId);

    List<EnrolledCourseDto> getEnrolledCoursesForCurrentUser();

    List<EnrolledCourseWithStatusDto> getEnrolledCoursesWithStatusForCurrentUser();
}
