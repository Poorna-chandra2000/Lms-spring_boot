package com.imperion.learn.LMS.Repositories;

import com.imperion.learn.LMS.Entities.Enrolled;
import com.imperion.learn.LMS.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrolledRepository extends JpaRepository<Enrolled,Long> {
    Optional<Enrolled> findByUserId(Long id);

    Optional<Enrolled> findByUser(User user);
}
