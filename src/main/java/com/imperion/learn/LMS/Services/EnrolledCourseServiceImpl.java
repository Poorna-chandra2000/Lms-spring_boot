package com.imperion.learn.LMS.Services;

import com.imperion.learn.LMS.Entities.Course;
import com.imperion.learn.LMS.Entities.EnrolledCourse;
import com.imperion.learn.LMS.PayLoad.UserDto;
import com.imperion.learn.LMS.Repositories.CourseRepository;
import com.imperion.learn.LMS.Repositories.EnrolledCourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrolledCourseServiceImpl implements EnrolledCourseService{

    private final CourseRepository courseRepository;
    private final EnrolledCourseRepository enrolledCourseRepository;
    private final ModelMapper modelMapper;

    // Get all users enrolled in a specific course
    public List<UserDto> getUsersEnrolledInCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        // Find all enrolled courses by course
        List<EnrolledCourse> enrolledCourses = enrolledCourseRepository.findByCourse(course);

        // Map the enrolled users to UserDto
        return enrolledCourses.stream()
                .map(enrolledCourse -> modelMapper.map(enrolledCourse.getEnrolled().getUser(), UserDto.class))
                .collect(Collectors.toList());
    }
}
