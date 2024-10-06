package com.imperion.learn.LMS.Repositories;


import com.imperion.learn.LMS.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //user is a entity here use what you created
    Optional<User> findByEmail(String username);


}
