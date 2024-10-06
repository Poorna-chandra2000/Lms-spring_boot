package com.imperion.learn.LMS.Security;


import com.imperion.learn.LMS.Entities.Session;
import com.imperion.learn.LMS.Entities.User;
import com.imperion.learn.LMS.Repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    //dependency Inject
    private final SessionRepository sessionRepository;

    private final int SESSION_LIMIT=2;//use dynamically according to user subsription make its part of entity and get input

    public  void generateNewSession(User user, String refreshToken){
        List<Session> userSession=sessionRepository.findByUser(user);
        if (userSession.size()==SESSION_LIMIT){
           //THEN WILL DELETE LAST USED SESSSION
           userSession.sort(Comparator.comparing(Session::getLastUsedAt));
           //now after sorting will get the first user session at the top
            //getfirst and del it from repo
            Session leastRecentlyUsedSession=userSession.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }
        //else create a new session
        Session newSession= Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        //just taking user and refreshtoken which was created



        sessionRepository.save(newSession);
    }

    public  void validateSession(String refreshToken){
       Session session= sessionRepository.findByRefreshToken(refreshToken)
               .orElseThrow(()->new SessionAuthenticationException("Session not found:"+refreshToken));
       //after adding session in AuthLoginService
        //update it here
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }



    public Boolean logoutuser(String refreshToken) {
        if(sessionRepository.existsByRefreshToken(refreshToken)){
            Optional<Session> token=sessionRepository.findByRefreshToken(refreshToken);
            sessionRepository.deleteById(token.get().getId());
            return true;
        }
   return false;
    }
}
