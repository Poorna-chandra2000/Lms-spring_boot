package com.imperion.learn.LMS.Controllers;


import com.imperion.learn.LMS.PayLoad.LoginDto;
import com.imperion.learn.LMS.PayLoad.LoginResponseDto;
import com.imperion.learn.LMS.PayLoad.SignUpDto;
import com.imperion.learn.LMS.PayLoad.UserDto;
import com.imperion.learn.LMS.Security.AuthLoginService;
import com.imperion.learn.LMS.Security.SessionService;
import com.imperion.learn.LMS.Security.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
//remember for signup and login must use these endpoints
//remember to permit everything what is needed in websecurityconfig
@RequestMapping("/auth")
public class AuthController_sign_login {
    //here we use UserService
    private final UserService userService;

    private final SessionService sessionService;

    //for Login seperate service
    private final AuthLoginService authLoginService;//seperate service for login

    //for siwtching security of cookies development or production from application properties
    @Value("${deploy.env}")
    private  String deployEnv;


    @PostMapping("/signup")
    ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(userService.signUp(signUpDto));
    }

    //useAuth AuthLogin Service
    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto logindto, HttpServletRequest request, HttpServletResponse response){


        LoginResponseDto loginResponseDto=authLoginService.login(logindto);//this included both acceess and refresh

        Cookie cookie=new Cookie("refreshToken",loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);//now no one ca access this cookie

        cookie.setSecure("production".equals(deployEnv));//{to avoid compramise of refreshtoken,so secure it more


        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){
     //Cookie[] cookies=  request.getCookies();//we can use forloop

        //or Simply use stream
       String refreshToken= Arrays.stream(request.getCookies())
                .filter(cookie ->"refreshToken".equals(cookie.getName()) )
                .findFirst()
               .map(cookie -> cookie.getValue())
                .orElseThrow(()->new AuthenticationServiceException("Refresh token not found inside the cookie"));

       LoginResponseDto loginResponseDto=authLoginService.refreshToken(refreshToken);

       return ResponseEntity.ok(loginResponseDto);
    }


    @DeleteMapping("/logout")
    public ResponseEntity<Boolean> logoutUser(HttpServletRequest request){
        String refreshToken= Arrays.stream(request.getCookies())
                .filter(cookie ->"refreshToken".equals(cookie.getName()) )
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(()->new AuthenticationServiceException("Refresh token not found inside the cookie"));
           if(refreshToken==null){
            return ResponseEntity.badRequest().body(false);
        }

     return ResponseEntity.ok(sessionService.logoutuser(refreshToken));

    }

}