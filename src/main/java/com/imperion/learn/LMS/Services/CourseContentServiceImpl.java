package com.imperion.learn.LMS.Services;

import com.imperion.learn.LMS.CustomException.ResourceNotFoundException;
import com.imperion.learn.LMS.Entities.Course;
import com.imperion.learn.LMS.Entities.CourseContent;
import com.imperion.learn.LMS.Entities.User;
import com.imperion.learn.LMS.PayLoad.CourseContentDto;
import com.imperion.learn.LMS.Repositories.CourseContentRepository;
import com.imperion.learn.LMS.Repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class CourseContentServiceImpl implements CourseContentService {
    private final CourseContentRepository courseContentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;




    @Override
    public CourseContentDto createCourseContent(CourseContentDto courseContentDto, Long courseId) {
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found, add content to the existing course or create a new course first"));

        CourseContent courseContent = modelMapper.map(courseContentDto, CourseContent.class);
        courseContent.setCourse(course); // Set the course reference
        courseContent.setAuthor(user);
        CourseContent savedContent = courseContentRepository.save(courseContent); // Save and get the saved instance

        course.getContentList().add(savedContent); // Update the course's content list
        courseRepository.save(course); // Save the updated course

        return modelMapper.map(savedContent, CourseContentDto.class); // Return the newly created CourseContentDto
    }

}
