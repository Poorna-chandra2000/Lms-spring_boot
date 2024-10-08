package com.imperion.learn.LMS.Security.utils;

import com.imperion.learn.LMS.Entities.User;
import com.imperion.learn.LMS.PayLoad.CourseDto;
import com.imperion.learn.LMS.Services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseSecurity {

private final CourseService courseService;

    public boolean isOwnerOfPost(Long courseId) {
        //to get current user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CourseDto course = courseService.getCourseById(courseId);//this getting post by id
        return course.getId().equals(user.getId());
    }

}
