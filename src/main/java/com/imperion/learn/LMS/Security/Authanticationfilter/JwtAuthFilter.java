package com.imperion.learn.LMS.Security.Authanticationfilter;

import com.imperion.learn.LMS.Entities.User;
import com.imperion.learn.LMS.Security.JWTservice;
import com.imperion.learn.LMS.Security.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component //y because will use this as bean for authentication
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    //needed for bearer token
//inject JWT service for this Authenticationfilter
    private  final JWTservice jwTservice;
    private final UserService userService;//remember to getbyid in user service first

    @Autowired
    @Qualifier("handlerExceptionResolver")//for excetion handling only
    private HandlerExceptionResolver handlerExceptionResolver;

    //now after doing all the steps below...now we need to Configure websecurityconfig class in config package which was also made as filters
    //we just need to add this JwtAuthFilter after or before there in WebSecurityConfig
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {


            final String requestTokenHeader = request.getHeader("Authorization");
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {//check the condition properly
                filterChain.doFilter(request, response);
                return;
            }

            String token = requestTokenHeader.split("Bearer ")[1];
            jwTservice.getUserIdFromToken(token);

            Long userId = jwTservice.getUserIdFromToken(token);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userService.getUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());//enum autherization
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }catch (Exception ex){
            handlerExceptionResolver.resolveException(request,response,null,ex);
        }
        //we can also log the response her if we want
        //we also might get exception
    }
}
