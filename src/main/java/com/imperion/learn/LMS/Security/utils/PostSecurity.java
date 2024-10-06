//package com.imperion.learn.LMS.Security.utils;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class PostSecurity {
//
//    private  final postService postservice;
//
//    public boolean isOwnerOfPost(Long id) {
//        //to get current user
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        postDto post = postservice.getById(id);//this getting post by id
//        return post.getAuthor().getId().equals(user.getId());
//    }
//
//}
