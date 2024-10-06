package com.imperion.learn.LMS.Services;

import com.imperion.learn.LMS.Entities.*;
import com.imperion.learn.LMS.Entities.enums.EnrollmentStatus;
import com.imperion.learn.LMS.PayLoad.CourseContentDto;
import com.imperion.learn.LMS.PayLoad.EnrolledCourseDto;
import com.imperion.learn.LMS.PayLoad.EnrolledDto;
import com.imperion.learn.LMS.Repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrolledServiceImpl implements EnrolledService{
    private final ModelMapper modelMapper;
    private final EnrolledRepository enrolledRepository;
    private final CourseRepository courseRepository;
    private final EnrolledCourseRepository enrolledCourseRepository;
    private final UserRepository userRepository;
    private final CourseContentRepository courseContentRepository;

    // Retrieve or create an enrolled cart for the current user
    public Enrolled getOrCreateEnrolledForCurrentUser() {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return enrolledRepository.findByUser(user)
                .orElseGet(() -> {
                    Enrolled enrolled = new Enrolled();
                    enrolled.setUser(user);
                    return enrolledRepository.save(enrolled);
                });
    }

    // Enroll the current user in a course
    public EnrolledCourseDto enrollUserInCourse(Long courseId) {
        Enrolled enrolled = getOrCreateEnrolledForCurrentUser();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        // Check if the user is already enrolled in the course
        boolean isAlreadyEnrolled = enrolled.getEnrolledCourses().stream()
                .anyMatch(enrolledCourse -> enrolledCourse.getCourse().getId().equals(courseId));

        if (isAlreadyEnrolled) {
            throw new RuntimeException("User is already enrolled in this course.");
        }

        // Create new EnrolledCourse and link it to the enrolled entity
        EnrolledCourse enrolledCourse = new EnrolledCourse();
        enrolledCourse.setCourse(course);
        enrolledCourse.setEnrolled(enrolled);
        enrolledCourse.setStatus(EnrollmentStatus.ENROLLED);  // Set the status to ENROLLED

        // Save the enrolled course
        enrolledCourseRepository.save(enrolledCourse);

        // Add the new EnrolledCourse to the user's enrollment list
        enrolled.getEnrolledCourses().add(enrolledCourse);
        enrolledRepository.save(enrolled);  // Update the enrolled entity with the new course

        return modelMapper.map(enrolledCourse,EnrolledCourseDto.class);
    }

    // Retrieve the content of a course the user is enrolled in
    public List<CourseContentDto> getCourseContent(Long courseId) {
        Enrolled enrolled = getOrCreateEnrolledForCurrentUser();

        // Check if the user is enrolled in the course
        boolean isEnrolled = enrolled.getEnrolledCourses().stream()
                .anyMatch(ec -> ec.getCourse().getId().equals(courseId) && ec.getStatus() == EnrollmentStatus.ENROLLED);

        if (!isEnrolled) {
            throw new RuntimeException("Access denied. You are not enrolled in this course.");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        // Fetch all content for the course
        return courseContentRepository.findByCourse(course).stream()
                .map(courseContent -> modelMapper.map(courseContent, CourseContentDto.class))
                .collect(Collectors.toList());
    }

    // Get the enrolled section for a specific user
    @Override
    public EnrolledDto getEnrolled(User user) {
        Enrolled enrolled = enrolledRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("No enrollment found for user: " + user.getEmail()));
        return modelMapper.map(enrolled, EnrolledDto.class);
    }

    public List<EnrolledCourseDto> getEnrolledCoursesForCurrentUser() {
        Enrolled enrolled = getOrCreateEnrolledForCurrentUser();

        // Map each EnrolledCourse to EnrolledCourseDto
        return enrolled.getEnrolledCourses().stream()
                .map(enrolledCourse -> modelMapper.map(enrolledCourse, EnrolledCourseDto.class))
                .collect(Collectors.toList());
    }



}

