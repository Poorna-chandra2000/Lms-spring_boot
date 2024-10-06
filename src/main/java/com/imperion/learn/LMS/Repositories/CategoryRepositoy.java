package com.imperion.learn.LMS.Repositories;

import com.imperion.learn.LMS.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoy extends JpaRepository<Category,Long> {
}
