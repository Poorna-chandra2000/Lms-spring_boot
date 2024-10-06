package com.imperion.learn.LMS.Security.utils;


import com.imperion.learn.LMS.Entities.enums.Permission;
import com.imperion.learn.LMS.Entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.imperion.learn.LMS.Entities.enums.Permission.*;
import static com.imperion.learn.LMS.Entities.enums.Role.*;


public class PermissionMapping {

    private static Map<Role, Set<Permission>> map=Map.of(
            USER,Set.of(USER_VIEW,POST_VIEW),
            CREATOR,Set.of(POST_CREATE,POST_UPDATE,POST_DELETE,POST_VIEW),
            ADMIN,Set.of(USER_VIEW,USER_DELETE,USER_UPDATE,POST_CREATE,POST_UPDATE,POST_DELETE,POST_VIEW)

    );

    public  static Set<SimpleGrantedAuthority> getAuthorities(Role role){
        return  map.get(role)
                .stream()//convert role to simplegranted authority
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}

