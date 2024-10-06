package com.imperion.learn.LMS.Repositories;

import com.imperion.learn.LMS.Entities.Session;

import com.imperion.learn.LMS.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);


    boolean existsByUser(User user);

    boolean existsByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken); // Correct method to delete by refresh token


}
