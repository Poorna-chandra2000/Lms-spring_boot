package com.imperion.learn.LMS.Services;

import com.imperion.learn.LMS.CustomException.ResourceNotFoundException;
import com.imperion.learn.LMS.Entities.Category;
import com.imperion.learn.LMS.Entities.Course;
import com.imperion.learn.LMS.Entities.EnrolledCourse;
import com.imperion.learn.LMS.Entities.enums.EnrollmentStatus;
import com.imperion.learn.LMS.PayLoad.CourseDto;
import com.imperion.learn.LMS.Repositories.CategoryRepositoy;
import com.imperion.learn.LMS.Repositories.CourseRepository;
import com.imperion.learn.LMS.Repositories.EnrolledCourseRepository;
import com.imperion.learn.LMS.Repositories.EnrolledRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    private final CategoryRepositoy categoryRepositoy;
    private final EnrolledCourseRepository enrolledCourseRepository;
    private final EnrolledRepository enrolledRepository;

    @Override
    public CourseDto createCourse(Long categoryId, CourseDto courseDto) {
        Category category= categoryRepositoy.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with Id:" + categoryId + " Not Found"));

        Course courseEntity=modelMapper.map(courseDto,Course.class);



        courseEntity.setCategory(category);
        courseRepository.save(courseEntity);

        category.getCourses().add(courseEntity);
       // categoryRepositoy.save(category);


        return modelMapper.map(courseEntity,CourseDto.class);
    }

    @Override
    public List<CourseDto> getAllCourse() {

        return courseRepository.findAll()
                .stream()
                .map(course -> modelMapper.map(course,CourseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getCourseByCategoryId(Long categoryId) {
        Category category= categoryRepositoy.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with Id:" + categoryId + " Not Found"));
        return courseRepository.findByCategory(category)
                .stream()
                .map(course -> modelMapper.map(course,CourseDto.class))
                .collect(Collectors.toList());
    }





    @Override
    public Boolean deleteCourseById(Long courseId) {
        if(courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
            return true;
        }
        return false;
    }


}
