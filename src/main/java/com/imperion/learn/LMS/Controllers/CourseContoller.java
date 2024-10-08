package com.imperion.learn.LMS.Controllers;

import com.imperion.learn.LMS.PayLoad.CourseDto;
import com.imperion.learn.LMS.Services.CategoryService;
import com.imperion.learn.LMS.Services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseContoller {

    private final CourseService courseService;
    private final CategoryService categoryService;


    //public and other users
    @PostMapping("/CreateCourse/{categoryId}")
    ResponseEntity<CourseDto> createNewCategory(@PathVariable Long categoryId, @RequestBody CourseDto courseDto){

        return ResponseEntity.ok(courseService.createCourse(categoryId,courseDto));
    }

    //for creators and admins
    @GetMapping("/getCourseByCategoryId/{categoryId}")
    ResponseEntity<List<CourseDto>> getCourseByCategoryId(@PathVariable Long categoryId){
        return ResponseEntity.ok(courseService.getCourseByCategoryId(categoryId));
    }

    //public
    @GetMapping("/allCourse")
    ResponseEntity<List<CourseDto>> getAllCourse(){
        return ResponseEntity.ok(courseService.getAllCourse());
    }

    @DeleteMapping("/delCourseById/{courseId}")
    ResponseEntity<Boolean> delById(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.deleteCourseById(courseId));
    }

    //Authors can get their courses in their dashboard
    @GetMapping("/allCourse/author")
    ResponseEntity<List<CourseDto>> getAllCourseByAuthor(){
        return ResponseEntity.ok(courseService.getAllCourseByAuthor());
    }


    @DeleteMapping("/delCourseByAuthor/{courseId}")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#courseId)")
    ResponseEntity<Boolean> delByAuthor(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.delByAuthor(courseId));
    }



}
