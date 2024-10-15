package com.imperion.learn.LMS.Controllers;

import com.imperion.learn.LMS.Entities.Course;
import com.imperion.learn.LMS.Entities.EnrolledCourse;
import com.imperion.learn.LMS.Entities.User;
import com.imperion.learn.LMS.PayLoad.*;
import com.imperion.learn.LMS.Repositories.CourseRepository;
import com.imperion.learn.LMS.Services.EnrolledCourseService;
import com.imperion.learn.LMS.Services.EnrolledService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enrolled")
public class EnrolledController {

    private final EnrolledService enrolledService;

    private final EnrolledCourseService enrolledCourseService;
    private final CourseRepository courseRepository;

    @GetMapping
    ResponseEntity<EnrolledDto> getEnrolled(){
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(enrolledService.getEnrolled(user));
    }
    // Endpoint to enroll a user in a course must be logged in
    @Secured({"ROLE_USER", "ROLE_ADMIN","ROLE_CREATOR"})
    @PostMapping("/enroll/{courseId}")
    public ResponseEntity<EnrolledCourseDto> enrollUserInCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrolledService.enrollUserInCourse(courseId));
    }

    // Endpoint to get the list of course content for an enrolled course
    @Secured({"ROLE_USER", "ROLE_ADMIN","ROLE_CREATOR"})
    @GetMapping("/course-content/{courseId}")
    public ResponseEntity<List<CourseContentDto>> getCourseContent(@PathVariable Long courseId) {
        List<CourseContentDto> courseContent = enrolledService.getCourseContent(courseId);
        return ResponseEntity.ok(courseContent);
    }


    @Secured({"ROLE_USER", "ROLE_ADMIN","ROLE_CREATOR"})
    @PreAuthorize("ADMIN")
    @GetMapping("/courses")
    public ResponseEntity<List<EnrolledCourseDto>> getEnrolledCourses() {
        List<EnrolledCourseDto> enrolledCourses = enrolledService.getEnrolledCoursesForCurrentUser();
        return ResponseEntity.ok(enrolledCourses);
    }

    //to get users who are enrolled in this course
    @Secured({"ROLE_ADMIN","ROLE_CREATOR"})
    @GetMapping("/users-enrolled/{courseId}")
    public  ResponseEntity<List<UserDto>> getUsersEnrolledInCourse(@PathVariable Long courseId){
        return ResponseEntity.ok(enrolledCourseService.getUsersEnrolledInCourse(courseId));
    }

     ///to get the status of courses like enrolled completed
    @GetMapping("/status")
    public ResponseEntity<List<EnrolledCourseWithStatusDto>> getEnrolledCoursesWithStatusForCurrentUser(){
        return ResponseEntity.ok(enrolledService.getEnrolledCoursesWithStatusForCurrentUser());
    }

    // Endpoint to mark a course as complete
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_CREATOR"})
    @PostMapping("/complete/{courseId}")
    public ResponseEntity<String> markCourseAsComplete(@PathVariable Long courseId) {
        enrolledService.markCourseAsComplete(courseId);
        return ResponseEntity.ok("Course completed successfully.");
    }

}
