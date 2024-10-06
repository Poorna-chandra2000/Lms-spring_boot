package com.imperion.learn.LMS.Security;

import com.imperion.learn.LMS.Entities.User;
import com.imperion.learn.LMS.PayLoad.LoginDto;
import com.imperion.learn.LMS.PayLoad.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthLoginService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTservice jwTservice;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;


    //now these acccess and refreshtokens connected to LoginResponseDto
    public LoginResponseDto login(LoginDto logindto) {
        //for this we need AuthunticateManager
        //(AuthenticaionManager(for login) in AuthLogin service)
        //create a bean of AuthenticationManager inn webSecurityConfig
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logindto.getEmail(),logindto.getPassword())
        );

        User user=(User) authentication.getPrincipal();
//        String token=jwTservice.generateToken(user);
        //return directly
//        return token;
        String accessToken=jwTservice.generateAccessToken(user);
        String refreshToken= jwTservice.generateRefreshToken(user);

        //if you want sessions for login just inject Sessionservice
        //and update here
        //next check the session is valid or not in the below method of refreshtoken
        sessionService.generateNewSession(user,refreshToken);

        //change the method type to loginresponseDto as your returning LoginresponseDto service
        return new LoginResponseDto(user.getId(), accessToken,refreshToken);

    }


    //To generate refreshToken
    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId=jwTservice.getUserIdFromToken(refreshToken);

        //* sessionadd this only if you having sessions and go to session service and update it
        sessionService.validateSession(refreshToken);

        User user=userService.getUserById(userId);

        //*session
        String accessToken=jwTservice.generateAccessToken(user);
        return new LoginResponseDto(user.getId(),accessToken,refreshToken);
    }


}
