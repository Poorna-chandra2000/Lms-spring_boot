package com.imperion.learn.LMS.Repositories;

import com.imperion.learn.LMS.Entities.Category;
import com.imperion.learn.LMS.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {


    List<Course> findByCategory(Category category);
}
