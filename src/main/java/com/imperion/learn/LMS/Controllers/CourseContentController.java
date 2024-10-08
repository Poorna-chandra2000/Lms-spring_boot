package com.imperion.learn.LMS.Controllers;

import com.imperion.learn.LMS.PayLoad.CourseContentDto;
import com.imperion.learn.LMS.Services.CourseContentService;
import com.imperion.learn.LMS.Services.EnrolledService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CourseContentController {


    private final CourseContentService courseContentService;
    private final EnrolledService enrolledService;

    @PostMapping("/createCourseContent/{courseId}")
    ResponseEntity<CourseContentDto> createCourseContent(@RequestBody CourseContentDto courseContentDto,@PathVariable Long courseId){
        return ResponseEntity.ok(courseContentService.createCourseContent(courseContentDto,courseId));
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN","ROLE_CREATOR"})
    @GetMapping("courseContent/{courseId}")
    public ResponseEntity<List<CourseContentDto>> getCourseContent(@PathVariable Long courseId) {

        return ResponseEntity.ok(enrolledService.getCourseContent(courseId));
    }
}
