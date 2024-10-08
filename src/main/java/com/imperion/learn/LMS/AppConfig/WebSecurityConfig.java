package com.imperion.learn.LMS.AppConfig;


import com.imperion.learn.LMS.Security.Authanticationfilter.JwtAuthFilter;
import com.imperion.learn.LMS.Security.OAuthSuccessHandlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.imperion.learn.LMS.Entities.enums.Permission.*;
import static com.imperion.learn.LMS.Entities.enums.Role.ADMIN;
import static com.imperion.learn.LMS.Entities.enums.Role.CREATOR;


@Configuration //if we comment out this thn it will auth user and pass in application properties
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)//this enables Secure methods
public class WebSecurityConfig {//this is web security filter
    //inject jwtauthfilter that you created in filter package,now go down and addbefore or after
    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }//now use this in user service to login


    //keep public routs as public
    private  static final String[] publicRoutes={
      "/api","api/allCategory","/error","/auth/**","/home.html"//** means all routs of selected route is permitted or u can authenticate
    };


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(auth-> // if is use this below /post/** and permit all thn i can access everything without authentication"
                        auth.requestMatchers(publicRoutes).permitAll()//here make sure auth is permistted from Authsignuplogin controller(avoid 403 error)
                                .requestMatchers("/users").permitAll()
                                .requestMatchers(HttpMethod.GET,"/users").hasRole(ADMIN.name())
                                .requestMatchers("/session/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"api/course/**").permitAll()
                                //.requestMatchers(HttpMethod.GET,"/api/**").hasAuthority(POST_VIEW.name())
                                .requestMatchers(HttpMethod.POST,"/api/category/**").hasAnyRole(ADMIN.name(),CREATOR.name())
                                //to pass authorities for these authorities you have to map roles with permisions
                                .requestMatchers(HttpMethod.POST,"/api/course/**").hasAnyAuthority(POST_CREATE.name(),POST_UPDATE.name(),POST_DELETE.name())
                                .requestMatchers(HttpMethod.GET,"/api/**").hasAuthority(POST_VIEW.name())
                                .requestMatchers(HttpMethod.DELETE,"/api/**").hasAnyRole(ADMIN.name())
                                .anyRequest().authenticated())//all other except public needs authentication
                .csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(sessionConfig->sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))//when we use this we have to login every single time
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)//i.e jwt filter is added before userpasswordauthentication
                .oauth2Login(oauth2Config -> oauth2Config//this like is for Oauth2
                        .failureUrl("/login?error=true")//oauth2
                        .successHandler(oAuth2SuccessHandler)//now just create new handler package and import this class
                );
                //.formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

 }
