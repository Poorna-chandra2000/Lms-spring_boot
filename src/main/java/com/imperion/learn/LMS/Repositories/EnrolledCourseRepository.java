package com.imperion.learn.LMS.Repositories;

import com.imperion.learn.LMS.Entities.Course;
import com.imperion.learn.LMS.Entities.EnrolledCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse,Long> {

    List<EnrolledCourse> findByCourse(Course course);
}
