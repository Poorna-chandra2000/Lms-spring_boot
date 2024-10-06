package com.imperion.learn.LMS.PayLoad;


import com.imperion.learn.LMS.Entities.enums.Role;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@Data
@NoArgsConstructor
public class SignUpDto {
    private  String email;
    private String password;
    private String name;

    private Set<Role> roles;
}
