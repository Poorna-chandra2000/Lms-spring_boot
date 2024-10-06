package com.imperion.learn.LMS.Services;

import com.imperion.learn.LMS.PayLoad.CourseDto;

import java.util.List;

public interface CourseService {
    CourseDto createCourse(Long categoryId, CourseDto courseDto);

    List<CourseDto> getAllCourse();


    List<CourseDto> getCourseByCategoryId(Long categoryId);

    Boolean deleteCourseById(Long courseId);
}
