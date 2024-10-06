package com.imperion.learn.LMS.Repositories;

import com.imperion.learn.LMS.Entities.Course;
import com.imperion.learn.LMS.Entities.CourseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseContentRepository extends JpaRepository<CourseContent,Long> {


    List<CourseContent> findByCourse(Course course); // Returns a list
    Optional<CourseContent> findById(Long id); // For fetching a specific content by ID
}
