package com.imperion.learn.LMS.Services;

import com.imperion.learn.LMS.PayLoad.CourseContentDto;

public interface CourseContentService {



    CourseContentDto createCourseContent(CourseContentDto courseContentDto, Long courseId);
}
