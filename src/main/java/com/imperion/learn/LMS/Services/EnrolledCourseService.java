package com.imperion.learn.LMS.Services;

import com.imperion.learn.LMS.Entities.EnrolledCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrolledCourseService extends JpaRepository<EnrolledCourse,Long> {
}
